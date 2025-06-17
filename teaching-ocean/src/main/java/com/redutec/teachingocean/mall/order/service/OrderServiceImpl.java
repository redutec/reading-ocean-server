package com.redutec.teachingocean.mall.order.service;

import com.redutec.core.criteria.OrderCriteria;
import com.redutec.core.dto.OrderDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Order;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.OrderMapper;
import com.redutec.core.repository.OrderRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.OrderSpecification;
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
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final AuthenticationService authenticationService;
    private final TeacherRepository teacherRepository;

    /**
     * 리딩오션몰 - 특정 상품 선택 - 상품 주문에 추가
     * @param addOrderItemsRequests 상품 주문에 추가할 요청 객체
     * @return 등록한 상품 주문 정보
     */
    @Override
    @Transactional
    public OrderDto.OrderItemResponse addOrderItems(
            OrderDto.AddOrderItemsRequestWrapper addOrderItemsRequests
    ) {
        // 현재 로그인한 교사의 ID 조회
        Long teacherId = authenticationService.getAuthenticatedTeacher().teacherId();
        // Teacher 엔티티 로드 및 검증
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("교사가 없습니다. teacherId: " + teacherId));
        // 소속 교육기관 엔티티 추출
        Institute institute = Optional.ofNullable(teacher.getInstitute())
                .orElseThrow(() -> new EntityNotFoundException("교사가 소속된 교육기관이 없습니다. teacherId: " + teacherId));
        // 상품들의 가격(할인을 적용한 총 금액)이 5만원 이하면 배송료를 3000원, 그 외에는 배송료를 0원
        // 수신인 정보를 조회하여 배송 엔티티를 생성
        // 새로운 상품 주문 엔티티를 생성
        Order order = orderRepository.findByInstitute(institute)
                .orElseGet(() -> Order.builder()
                        .institute(teacher.getInstitute())
                        .deliveryFee(0)       // TODO: 배송료 계산 로직 추가
                        .build()
                );
        // 상품 주문 엔티티 저장 후 응답 객체로 리턴
        return orderMapper.toResponseDto(orderMapper.toEntity(addOrderItemsRequests, order));
    }

    /**
     * 리딩오션몰 - 장바구니 - 현재 장바구니에 있는 상품들을 상품 주문에 추가
     * @return 등록한 상품 주문 정보
     */
    @Override
    @Transactional
    public OrderDto.OrderItemResponse addOrderItemsFromCart() {
        // 현재 로그인한 교육기관의 장바구니 엔티티를 조회
        // 장바구니에 있는 상품들을 상품 주문 엔티티로 변환하여 생성
        // 상품 주문 엔티티 저장 후 응답 객체로 리턴
        return null;
    }

    /**
     * 리딩오션몰 - 상품 주문 조회
     * @param getOrderItemRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교육기관의 상품 주문 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public OrderDto.OrderItemPageResponse find(OrderDto.GetOrderItemRequest getOrderItemRequest) {
        // 현재 로그인한 교사의 ID 조회
        Long teacherId = authenticationService.getAuthenticatedTeacher().teacherId();
        // Teacher 엔티티 로드 및 검증
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("교사가 없습니다. teacherId: " + teacherId));
        // 소속 교육기관 ID 추출
        Long instituteId = Optional.ofNullable(teacher.getInstitute())
                .map(Institute::getId)
                .orElseThrow(() -> new EntityNotFoundException("교사가 소속된 교육기관이 없습니다. teacherId: " + teacherId));
        // 페이징 설정
        Pageable pageable = (getOrderItemRequest.page() != null && getOrderItemRequest.size() != null)
                ? PageRequest.of(
                getOrderItemRequest.page(),
                getOrderItemRequest.size(),
                Sort.by("createdAt").descending()
        ) : Pageable.unpaged();
        // 검색 조건(criteria) 생성
        OrderCriteria criteria = orderMapper.toCriteria(instituteId, getOrderItemRequest);
        // Specification 기반 조회
        Page<Order> page = orderRepository.findAll(
                OrderSpecification.findWith(criteria),
                pageable
        );
        // 상품 주문 조회 결과 → DTO Page 변환 및 반환
        return orderMapper.toPageResponseDto(page);
    }
}