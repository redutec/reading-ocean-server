package com.redutec.core.mapper;

import com.redutec.core.criteria.OrderCriteria;
import com.redutec.core.dto.OrderItemDto;
import com.redutec.core.dto.OrderDto;
import com.redutec.core.dto.ProductDto;
import com.redutec.core.entity.Order;
import com.redutec.core.repository.OrderRepository;
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
public class OrderMapper {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    /**
     * addOrderItemsRequest DTO를 기반으로 InstituteOrder INSERT/UPDATE 엔티티를 생성합니다.
     *
     * @param addOrderItemsRequests 주문내역에 담을 상품들의 요청 정보
     * @param order 교육기관의 주문내역 엔티티
     * @return 등록/수정할 InstituteOrder 엔티티
     */
    public Order toEntity(
            OrderDto.AddOrderItemsRequestWrapper addOrderItemsRequests,
            Order order
    ) {
        // DTO에 담긴 상품들이 존재하면 주문내역에 담긴 상품에 추가
        Optional.ofNullable(addOrderItemsRequests.addOrderItemsRequests())
                .ifPresent(requests -> requests.forEach(request ->
                        productRepository.findById(request.productId())
                                .ifPresent(product -> order.addItem(product, request.quantity()))
                ));
        // 주문내역(교육기관) 등록 엔티티 리턴
        return order;
    }
    
    /**
     * 이 메서드는 현재 GetOrderItemRequest 객체를 기반으로
     * InstituteOrderCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 교육기관의 주문내역에 있는 상품 검색 조건을 구성할 때 사용됩니다.
     *
     * @param getOrderItemRequest 교육기관의 주문내역에 있는 상품 검색 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 InstituteOrderCriteria 객체
     */
    public OrderCriteria toCriteria(
            Long instituteId,
            OrderDto.GetOrderItemRequest getOrderItemRequest
    ) {
        return new OrderCriteria(
                instituteId,
                getOrderItemRequest.productName()
        );
    }

    /**
     * InstituteOrder 엔티티를 기반으로 응답용 InstituteOrderResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param order 변환할 InstituteOrder 엔티티 (null 가능)
     * @return InstituteOrder 엔티티의 데이터를 담은 InstituteOrderResponse DTO, instituteOrder가 null이면 null 반환
     */
    public OrderDto.OrderItemResponse toResponseDto(
            Order order
    ) {
        return Optional.ofNullable(order)
                .map(ci -> {
                    var itemsDto = ci.getItems().stream()
                            .map(orderItem -> {
                                var product = orderItem.getProduct();
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
                                return new OrderItemDto(
                                        productResponse,
                                        orderItem.getQuantity()
                                );
                            })
                            .collect(Collectors.toList());
                    return new OrderDto.OrderItemResponse(
                            itemsDto
                    );
                })
                .orElse(null);
    }

    /**
     * Page 형식의 InstituteOrder 엔티티 목록을 InstituteOrderPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param instituteOrderPage Page 형태로 조회된 InstituteOrder 엔티티 목록 (null 가능)
     * @return InstituteOrder 엔티티 리스트와 페이지 정보를 담은 InstituteOrderPageResponse DTO, instituteOrderPage가 null이면 null 반환
     */
    public OrderDto.OrderItemPageResponse toPageResponseDto(Page<Order> instituteOrderPage) {
        return Optional.ofNullable(instituteOrderPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new OrderDto.OrderItemPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}