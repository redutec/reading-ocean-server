package com.redutec.core.mapper;

import com.redutec.core.criteria.CartCriteria;
import com.redutec.core.dto.CartItemDto;
import com.redutec.core.dto.CartDto;
import com.redutec.core.dto.ProductDto;
import com.redutec.core.entity.Cart;
import com.redutec.core.repository.InstituteCartRepository;
import com.redutec.core.repository.ProductRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class CartMapper {
    private final InstituteCartRepository instituteCartRepository;
    private final ProductRepository productRepository;

    /**
     * addCartItemsRequest DTO를 기반으로 InstituteCart INSERT/UPDATE 엔티티를 생성합니다.
     *
     * @param addCartItemsRequests 장바구니에 담을 상품들의 요청 정보
     * @param cart 교육기관의 장바구니 엔티티
     * @return 등록/수정할 InstituteCart 엔티티
     */
    public Cart toEntity(
            CartDto.AddCartItemsRequestWrapper addCartItemsRequests,
            Cart cart
    ) {
        // DTO에 담긴 상품들이 존재하면 장바구니에 담긴 상품에 추가
        Optional.ofNullable(addCartItemsRequests.addCartItemsRequests())
                .ifPresent(requests -> requests.forEach(request ->
                        productRepository.findById(request.productId())
                                .ifPresent(product -> cart.addItem(product, request.quantity()))
                ));
        // 장바구니(교육기관) 등록 엔티티 리턴
        return cart;
    }
    
    /**
     * 이 메서드는 현재 GetCartItemRequest 객체를 기반으로
     * InstituteCartCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 교육기관의 장바구니에 있는 상품 검색 조건을 구성할 때 사용됩니다.
     *
     * @param getCartItemRequest 교육기관의 장바구니에 있는 상품 검색 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 InstituteCartCriteria 객체
     */
    public CartCriteria toCriteria(
            Long instituteId,
            CartDto.GetCartItemRequest getCartItemRequest
    ) {
        return new CartCriteria(
                instituteId,
                getCartItemRequest.productName()
        );
    }

    /**
     * InstituteCart 엔티티를 기반으로 응답용 InstituteCartResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param cart 변환할 InstituteCart 엔티티 (null 가능)
     * @return InstituteCart 엔티티의 데이터를 담은 InstituteCartResponse DTO, instituteCart가 null이면 null 반환
     */
    public CartDto.CartItemResponse toResponseDto(
            Cart cart
    ) {
        return Optional.ofNullable(cart)
                .map(ci -> {
                    var itemsDto = ci.getItems().stream()
                            .map(cartItem -> {
                                var product = cartItem.getProduct();
                                var productResponse = new ProductDto.ProductResponse(
                                        product.getId(),
                                        product.getName(),
                                        product.getDetails(),
                                        product.getPrice(),
                                        product.getDiscountPercentage(),
                                        product.getPrice() * (100 - product.getDiscountPercentage()) / 100,
                                        product.getAttachmentFileName(),
                                        product.getCategory(),
                                        product.getStatus(),
                                        product.getCreatedAt(),
                                        product.getUpdatedAt()
                                );
                                return new CartItemDto(
                                        productResponse,
                                        cartItem.getQuantity()
                                );
                            })
                            .collect(Collectors.toList());
                    return new CartDto.CartItemResponse(
                            itemsDto
                    );
                })
                .orElse(null);
    }

    /**
     * Page 형식의 InstituteCart 엔티티 목록을 InstituteCartPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param instituteCartPage Page 형태로 조회된 InstituteCart 엔티티 목록 (null 가능)
     * @return InstituteCart 엔티티 리스트와 페이지 정보를 담은 InstituteCartPageResponse DTO, instituteCartPage가 null이면 null 반환
     */
    public CartDto.CartItemPageResponse toPageResponseDto(Page<Cart> instituteCartPage) {
        return Optional.ofNullable(instituteCartPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new CartDto.CartItemPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}