package com.redutec.admin.product.service;

import com.redutec.core.dto.ProductDto;
import com.redutec.core.mapper.ProductMapper;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.entity.Product;
import com.redutec.core.repository.ProductRepository;
import com.redutec.core.specification.ProductSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final FileUtil fileUtil;

    /**
     * 판매상품 등록
     * @param createProductRequest 판매상품 등록 정보를 담은 DTO
     * @return 등록된 판매상품 정보
     */
    @Override
    @Transactional
    public ProductDto.ProductResponse create(ProductDto.CreateProductRequest createProductRequest) {
        // 등록 요청 객체에 첨부 파일이 있다면 업로드 후 파일명을 가져오기
        String attachedFileName = Optional.ofNullable(createProductRequest.attachedFile())
                .filter(attachedFile -> !attachedFile.isEmpty())
                .map(attachedFile -> {
                    FileUploadResult result = fileUtil.uploadFile(attachedFile, "/product");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        // 판매상품 등록
        return productMapper.toResponseDto(productRepository.save(productMapper.toCreateEntity(
                createProductRequest,
                attachedFileName
        )));
    }

    /**
     * 조건에 맞는 판매상품 목록 조회
     * @param findProductRequest 조회 조건을 담은 DTO
     * @return 조회된 판매상품 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDto.ProductPageResponse find(ProductDto.FindProductRequest findProductRequest) {
        return productMapper.toPageResponseDto(productRepository.findAll(
                ProductSpecification.findWith(productMapper.toCriteria(findProductRequest)),
                (findProductRequest.page() != null && findProductRequest.size() != null)
                        ? PageRequest.of(findProductRequest.page(), findProductRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 판매상품 조회
     * @param productId 판매상품 고유번호
     * @return 특정 판매상품 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDto.ProductResponse findById(Long productId) {
        return productMapper.toResponseDto(getProduct(productId));
    }

    /**
     * 특정 판매상품 수정
     * @param productId 수정할 판매상품의 ID
     * @param updateProductRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long productId, ProductDto.UpdateProductRequest updateProductRequest) {
        // 수정 요청 객체에 첨부 파일이 존재한다면 업로드 후 파일명을 가져오기
        String attachedFileName = Optional.ofNullable(updateProductRequest.attachedFile())
                .filter(attachedFile -> !attachedFile.isEmpty())
                .map(attachedFile -> {
                    FileUploadResult result = fileUtil.uploadFile(attachedFile, "/product");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        // 판매상품 수정
        productRepository.save(productMapper.toUpdateEntity(
                getProduct(productId),
                updateProductRequest,
                attachedFileName
        ));
    }

    /**
     * 특정 판매상품 삭제
     * @param productId 삭제할 판매상품의 ID
     */
    @Override
    @Transactional
    public void delete(Long productId) {
        productRepository.delete(getProduct(productId));
    }

    /**
     * 특정 판매상품 엔티티 조회
     * @param productId 판매상품 고유번호
     * @return 특정 판매상품 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("판매상품을 찾을 수 없습니다. productId = " + productId));
    }
}