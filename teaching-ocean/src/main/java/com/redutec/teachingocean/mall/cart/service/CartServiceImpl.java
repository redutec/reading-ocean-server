package com.redutec.teachingocean.mall.cart.service;

import com.redutec.core.criteria.CartCriteria;
import com.redutec.core.dto.CartDto;
import com.redutec.core.entity.Cart;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.CartMapper;
import com.redutec.core.repository.InstituteCartRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.CartSpecification;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartMapper cartMapper;
    private final InstituteCartRepository instituteCartRepository;
    private final AuthenticationService authenticationService;
    private final TeacherRepository teacherRepository;

    /**
     * 리딩오션몰 - 특정 상품 선택 - 장바구니에 추가
     *
     * @param addCartItemsRequests 장바구니 등록 정보를 담은 DTO List
     * @return 현재 로그인한 교육기관의 장바구니 정보
     */
    @Override
    @Transactional
    public CartDto.CartItemResponse addCartItems(
            CartDto.AddCartItemsRequestWrapper addCartItemsRequests
    ) {
        // 현재 로그인한 교육기관이 생성한 장바구니 엔티티가 있는지 조회(보유한 장바구니가 없으면 새로운 장바구니 엔티티를 생성)
        Long teacherId = authenticationService.getAuthenticatedTeacher().teacherId();
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("교사가 존재하지 않습니다. teacherId: " + teacherId));
        authenticationService.validateAuthenticationStatus(teacher);
        Institute institute = Optional.ofNullable(teacher.getInstitute())
                .orElseThrow(() -> new EntityNotFoundException("소속 교육기관이 없습니다."));
        Cart cart = instituteCartRepository
                .findByInstituteId(institute.getId())
                .orElseGet(() ->
                        Cart.builder()
                                .institute(institute)      // ★ 여기만 세팅
                                .build()
                );
        // 장바구니 엔티티를 저장하고 장바구니에 담긴 상품(상품정보와 수량)들을 담은 응답 객체를 리턴
        return cartMapper.toResponseDto(
                instituteCartRepository.save(
                        cartMapper.toEntity(addCartItemsRequests, cart)
                )
        );
    }

    /**
     * 리딩오션몰 - 장바구니 조회
     *
     * @param getCartItemRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교육기관의 장바구니에 담긴 상품 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public CartDto.CartItemPageResponse getCartItems(
            CartDto.GetCartItemRequest getCartItemRequest
    ) {
        // 1) 현재 로그인한 교사의 teacherId 조회 및 엔티티 로드
        Long teacherId = authenticationService.getAuthenticatedTeacher().teacherId();
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new EntityNotFoundException("교사가 존재하지 않습니다. teacherId: " + teacherId)
                );
        // 2) 교사 계정 상태 검증
        authenticationService.validateAuthenticationStatus(teacher);
        // 3) 소속 교육기관 ID 추출
        Long instituteId = Optional.ofNullable(teacher.getInstitute())
                .map(Institute::getId)
                .orElseThrow(() ->
                        new EntityNotFoundException("교사가 소속된 교육기관이 없습니다. teacherId: " + teacherId)
                );
        // 4) 페이징 설정
        Pageable pageable = (getCartItemRequest.page() != null && getCartItemRequest.size() != null)
                ? PageRequest.of(getCartItemRequest.page(), getCartItemRequest.size())
                : Pageable.unpaged();
        // 5) 검색 조건(criteria) 생성 (기관 ID + 상품명)
        CartCriteria cartCriteria = new CartCriteria(
                instituteId,
                getCartItemRequest.productName()
        );
        // 6) Specification 기반 조회
        Page<Cart> instituteCarts = instituteCartRepository.findAll(
                CartSpecification.findWith(cartCriteria),
                pageable
        );
        // 7) 페이징 DTO 변환 및 반환
        return cartMapper.toPageResponseDto(instituteCarts);
    }

    /**
     * 리딩오션몰 - 장바구니 비우기
     */
    @Override
    @Transactional
    public void clearCart() {
        // 1) 현재 로그인한 교사의 teacherId 조회 및 엔티티 로드
        Long teacherId = authenticationService.getAuthenticatedTeacher().teacherId();
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new EntityNotFoundException("교사가 존재하지 않습니다. teacherId: " + teacherId)
                );
        // 2) 교사 계정 상태 검증
        authenticationService.validateAuthenticationStatus(teacher);
        // 3) 소속 교육기관 ID 추출
        Long instituteId = Optional.ofNullable(teacher.getInstitute())
                .map(Institute::getId)
                .orElseThrow(() ->
                        new EntityNotFoundException("교사가 소속된 교육기관이 없습니다. teacherId: " + teacherId)
                );
        // 4) 장바구니 조회 및 비우기
        instituteCartRepository.findByInstituteId(instituteId)
                .ifPresent(cart -> {
                    cart.getItems().clear();
                    instituteCartRepository.save(cart);
                });
    }
}