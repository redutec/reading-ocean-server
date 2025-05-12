package com.redutec.admin.product.mapper;

import com.redutec.admin.product.dto.ProductDto;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.criteria.ProductCriteria;
import com.redutec.core.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class ProductMapper {
    private final FileUtil fileUtil;

    /**
     * CreateProductRequest DTO를 기반으로 Product 엔티티를 생성합니다.
     *
     * @param createProductRequest 판매상품 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Product 엔티티
     */
    public Product toEntity(ProductDto.CreateProductRequest createProductRequest) {
        // 첨부 파일이 존재하는 경우 파일을 업로드하고 파일명을 가져오기(파일이 없으면 파일명은 null)
        String attachedFileName = Optional.ofNullable(createProductRequest.attachedFile())
                .filter(attachedFile -> !attachedFile.isEmpty())
                .map(attachedFile -> {
                    FileUploadResult result = fileUtil.uploadFile(attachedFile, "/product");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        // Product 엔티티 Build
        return Product.builder()
                .name(createProductRequest.name())
                .details(createProductRequest.details())
                .price(createProductRequest.price())
                .discountPercentage(createProductRequest.discountPercentage())
                .attachedFileName(attachedFileName)
                .category(createProductRequest.category())
                .status(createProductRequest.status())
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindProductRequest 객체를 기반으로
     * ProductCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 판매상품 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 ProductCriteria 객체
     */
    public ProductCriteria toCriteria(ProductDto.FindProductRequest findProductRequest) {
        return new ProductCriteria(
                findProductRequest.productIds(),
                findProductRequest.name(),
                findProductRequest.details(),
                findProductRequest.minimumPrice(),
                findProductRequest.maximumPrice(),
                findProductRequest.minimumDiscountPercentage(),
                findProductRequest.maximumDiscountPercentage(),
                findProductRequest.categories(),
                findProductRequest.statuses()
        );
    }

    /**
     * Product 엔티티를 기반으로 응답용 ProductResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param product 변환할 Product 엔티티 (null 가능)
     * @return Product 엔티티의 데이터를 담은 ProductResponse DTO, product가 null이면 null 반환
     */
    public ProductDto.ProductResponse toResponseDto(Product product) {
        // 할인율이 있다면 할인된 요금을 계산(소수점 없이 반올림 처리)
        Integer priceAfterDiscount = Optional.ofNullable(product)
                .map(sp -> (int) Math.round(
                        sp.getPrice() * (100 - Optional.ofNullable(sp.getDiscountPercentage()).filter(d -> d > 0).orElse(0)) / 100.0
                ))
                .orElse(null);
        return Optional.ofNullable(product)
                .map(p -> new ProductDto.ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getDetails(),
                        p.getPrice(),
                        p.getDiscountPercentage(),
                        priceAfterDiscount,
                        p.getAttachedFileName(),
                        p.getCategory(),
                        p.getStatus(),
                        p.getCreatedAt(),
                        p.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Product 엔티티 목록을 ProductPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param productPage Page 형태로 조회된 Product 엔티티 목록 (null 가능)
     * @return Product 엔티티 리스트와 페이지 정보를 담은 ProductPageResponse DTO, productPage가 null이면 null 반환
     */
    public ProductDto.ProductPageResponse toPageResponseDto(Page<Product> productPage) {
        return Optional.ofNullable(productPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new ProductDto.ProductPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}