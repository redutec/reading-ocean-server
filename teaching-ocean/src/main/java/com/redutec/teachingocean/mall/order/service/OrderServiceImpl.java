package com.redutec.teachingocean.mall.order.service;

import com.redutec.core.criteria.InstituteOrderCriteria;
import com.redutec.core.dto.InstituteOrderDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.InstituteOrder;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.InstituteOrderMapper;
import com.redutec.core.repository.InstituteOrderRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.InstituteOrderSpecification;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final InstituteOrderMapper instituteOrderMapper;
    private final InstituteOrderRepository instituteOrderRepository;
    private final AuthenticationService authenticationService;
    private final TeacherRepository teacherRepository;

    /**
     * 리딩오션몰 - 특정 상품 선택 - 주문내역에 추가
     *
     * @param addOrderItemsRequests 주문내역(교육기관) 등록 정보를 담은 DTO List
     * @return 등록된 주문내역(교육기관) 정보
     */
    @Override
    @Transactional
    public InstituteOrderDto.OrderItemResponse addOrderItems(
            InstituteOrderDto.AddOrderItemsRequestWrapper addOrderItemsRequests
    ) {
        // 1) 현재 로그인한 교사의 ID 조회
        Long teacherId = authenticationService.getAuthenticatedTeacher().teacherId();
        // 2) Teacher 엔티티 로드 및 검증
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("교사가 없습니다. teacherId: " + teacherId));
        // 3) 소속 교육기관 엔티티 추출
        Institute institute = Optional.ofNullable(teacher.getInstitute())
                .orElseThrow(() -> new EntityNotFoundException("교사가 소속된 교육기관이 없습니다. teacherId: " + teacherId));
        // 4) 상품들의 가격(할인을 적용한 총 금액)이 5만원 이하면 배송료를 3000원, 그 외에는 배송료를 0원
        // 5) 수신인 정보를 조회하여 배송 엔티티를 생성
        // 6) 새로운 주문내역 엔티티를 생성
        InstituteOrder instituteOrder = instituteOrderRepository.findByInstitute(institute)
                .orElseGet(() -> InstituteOrder.builder()
                        .institute(teacher.getInstitute())
                        .deliveryFee(0)       // TODO: 배송료 계산 로직 추가
                        .build()
                );
        // 7) DTO → Entity 매핑 (addItem 호출)
        instituteOrderMapper.toEntity(addOrderItemsRequests, instituteOrder);
        // 8) 저장
        InstituteOrder savedOrder = instituteOrderRepository.save(instituteOrder);
        // 9) 저장된 엔티티 → DTO 변환 후 반환
        return instituteOrderMapper.toResponseDto(savedOrder);
    }

    /**
     * 리딩오션몰 - 장바구니 - 현재 로그인한 교육기관의 장바구니에 있는 상품들을 주문내역에 추가
     *
     * @return 등록된 주문내역(교육기관) 정보
     */
    @Override
    @Transactional
    public InstituteOrderDto.OrderItemResponse addOrderItemsFromCart() {
        return null;
    }

    /**
     * 리딩오션몰 - 주문내역 조회
     *
     * @param getOrderItemRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교육기관의 주문내역에 담긴 상품 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteOrderDto.OrderItemPageResponse getOrderItems(InstituteOrderDto.GetOrderItemRequest getOrderItemRequest) {
        // 1) 현재 로그인한 교사의 ID 조회
        Long teacherId = authenticationService.getAuthenticatedTeacher().teacherId();
        // 2) Teacher 엔티티 로드 및 검증
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("교사가 없습니다. teacherId: " + teacherId));
        // 3) 소속 교육기관 ID 추출
        Long instituteId = Optional.ofNullable(teacher.getInstitute())
                .map(Institute::getId)
                .orElseThrow(() -> new EntityNotFoundException("교사가 소속된 교육기관이 없습니다. teacherId: " + teacherId));
        // 4) 페이징 설정
        Pageable pageable = (getOrderItemRequest.page() != null && getOrderItemRequest.size() != null)
                ? PageRequest.of(
                getOrderItemRequest.page(),
                getOrderItemRequest.size(),
                Sort.by("createdAt").descending()
        ) : Pageable.unpaged();
        // 5) 검색 조건(criteria) 생성
        InstituteOrderCriteria criteria = instituteOrderMapper.toCriteria(instituteId, getOrderItemRequest);
        // 6) Specification 기반 조회
        Page<InstituteOrder> page = instituteOrderRepository.findAll(
                InstituteOrderSpecification.findWith(criteria),
                pageable
        );
        // 7) 조회 결과 → DTO Page 변환 및 반환
        return instituteOrderMapper.toPageResponseDto(page);
    }
}