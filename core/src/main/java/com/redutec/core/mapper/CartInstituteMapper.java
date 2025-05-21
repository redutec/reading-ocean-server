package com.redutec.core.mapper;

import com.redutec.core.criteria.CartInstituteCriteria;
import com.redutec.core.dto.CartInstituteDto;
import com.redutec.core.dto.CartItemDto;
import com.redutec.core.dto.ProductDto;
import com.redutec.core.entity.CartInstitute;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Product;
import com.redutec.core.repository.ProductRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class CartInstituteMapper {
    private final ProductRepository productRepository;

    /**
     * addItemToCartInstituteRequests DTO를 기반으로 CartInstitute 등록/수정 엔티티를 생성합니다.
     *
     * @param addItemToCartInstituteRequests 장바구니에 담을 상품들의 정보
     * @param institute 장바구니를 소유한 교육기관 엔티티
     * @return 등록/수정할 CartInstitute 엔티티
     */
    public CartInstitute toEntity(
            List<CartInstituteDto.AddItemToCartInstituteRequest> addItemToCartInstituteRequests,
            Institute institute
    ) {
        // 등록할 장바구니(교육기관) 엔티티를 생성
        CartInstitute cartInstitute = CartInstitute.builder()
                .institute(institute)
                .build();
        // DTO에 담긴 상품들을 장바구니에 담긴 상품에 추가
        Optional.ofNullable(addItemToCartInstituteRequests)
                .ifPresent(requests -> requests.forEach(req -> {
                    Product product = productRepository.getReferenceById(req.productId());
                    cartInstitute.addItem(product, req.quantity());
                }));
        // 장바구니(교육기관) 등록 엔티티 리턴
        return cartInstitute;
    }
    
    /**
     * 이 메서드는 현재 FindCartInstituteRequest 객체를 기반으로
     * CartInstituteCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 구독(교육기관) 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findCartInstituteRequest 구독(교육기관) 검색 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 CartInstituteCriteria 객체
     */
    public CartInstituteCriteria toCriteria(
            CartInstituteDto.FindCartInstituteRequest findCartInstituteRequest
    ) {
        return new CartInstituteCriteria(
                findCartInstituteRequest.instituteIds(),
                findCartInstituteRequest.productIds()
        );
    }

    /**
     * CartInstitute 엔티티를 기반으로 응답용 CartInstituteResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param cartInstitute 변환할 CartInstitute 엔티티 (null 가능)
     * @return CartInstitute 엔티티의 데이터를 담은 CartInstituteResponse DTO, cartInstitute가 null이면 null 반환
     */
    public CartInstituteDto.CartInstituteResponse toResponseDto(
            CartInstitute cartInstitute
    ) {
        return Optional.ofNullable(cartInstitute)
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
                                        product.getAttachedFileName(),
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
                    return new CartInstituteDto.CartInstituteResponse(
                            ci.getInstitute().getId(),
                            itemsDto
                    );
                })
                .orElse(null);
    }

    /**
     * Page 형식의 CartInstitute 엔티티 목록을 CartInstitutePageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param cartInstitutePage Page 형태로 조회된 CartInstitute 엔티티 목록 (null 가능)
     * @return CartInstitute 엔티티 리스트와 페이지 정보를 담은 CartInstitutePageResponse DTO, cartInstitutePage가 null이면 null 반환
     */
    public CartInstituteDto.CartInstitutePageResponse toPageResponseDto(Page<CartInstitute> cartInstitutePage) {
        return Optional.ofNullable(cartInstitutePage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new CartInstituteDto.CartInstitutePageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}