package com.redutec.admin.book.mapper;

import com.redutec.admin.book.dto.BookDto;
import com.redutec.core.criteria.BookCriteria;
import com.redutec.core.entity.Branch;
import com.redutec.core.entity.Book;
import com.redutec.core.entity.Teacher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Component
public class BookMapper {
    /**
     * CreateBookRequest DTO를 기반으로 Book 엔티티를 생성합니다.
     *
     * @param createBookRequest 도서 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Book 엔티티
     */
    public Book toEntity(
            BookDto.CreateBookRequest createBookRequest,
            Branch branch
    ) {
        return Book.builder()
                .name(createBookRequest.name())
                .businessRegistrationName(createBookRequest.businessRegistrationName())
                .address(createBookRequest.address())
                .zipCode(createBookRequest.zipCode())
                .phoneNumber(createBookRequest.phoneNumber())
                .url(createBookRequest.url())
                .naverPlaceUrl(createBookRequest.naverPlaceUrl())
                .type(createBookRequest.type())
                .managementType(createBookRequest.managementType())
                .status(createBookRequest.status())
                .operationStatus(createBookRequest.operationStatus())
                .branch(branch)
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindBookRequest 객체를 기반으로
     * BookCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 도서 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 BookCriteria 객체
     */
    public BookCriteria toCriteria(
            BookDto.FindBookRequest findBookRequest
    ) {
        return new BookCriteria(
                findBookRequest.bookIds(),
                findBookRequest.name(),
                findBookRequest.businessRegistrationName(),
                findBookRequest.types(),
                findBookRequest.managementTypes(),
                findBookRequest.statuses(),
                findBookRequest.operationStatuses(),
                findBookRequest.branchIds()
        );
    }

    /**
     * Book 엔티티를 기반으로 응답용 BookResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param book 변환할 Book 엔티티 (null 가능)
     * @return Book 엔티티의 데이터를 담은 BookResponse DTO, book가 null이면 null 반환
     */
    public BookDto.BookResponse toResponseDto(
            Book book,
            Teacher chiefTeacher,
            Branch branch
    ) {
        return Optional.ofNullable(book)
                .map(in -> new BookDto.BookResponse(
                        in.getId(),
                        in.getName(),
                        in.getBusinessRegistrationName(),
                        in.getAddress(),
                        in.getZipCode(),
                        in.getPhoneNumber(),
                        in.getUrl(),
                        in.getNaverPlaceUrl(),
                        in.getType(),
                        in.getManagementType(),
                        in.getStatus(),
                        in.getOperationStatus(),
                        chiefTeacher != null ? chiefTeacher.getId() : null,
                        chiefTeacher != null ? chiefTeacher.getName() : null,
                        branch != null ? branch.getId() : null,
                        branch != null ? branch.getName() : null,
                        in.getCreatedAt(),
                        in.getUpdatedAt()
                ))
                .orElse(null);
    }
}