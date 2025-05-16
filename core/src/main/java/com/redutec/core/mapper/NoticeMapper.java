package com.redutec.core.mapper;

import com.redutec.core.config.FileUtil;
import com.redutec.core.criteria.NoticeCriteria;
import com.redutec.core.dto.NoticeDto;
import com.redutec.core.entity.Notice;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class NoticeMapper {
    private final FileUtil fileUtil;

    /**
     * CreateNoticeRequest DTO를 기반으로 Notice 등록 엔티티를 생성합니다.
     *
     * @param createNoticeRequest 공지사항 등록에 필요한 데이터를 담은 DTO
     * @param attachedFileName 공지사항의 첨부 파일
     * @return 등록할 Notice 엔티티
     */
    public Notice toCreateEntity(
            NoticeDto.CreateNoticeRequest createNoticeRequest,
            String attachedFileName
    ) {
        return Notice.builder()
                .domain(createNoticeRequest.domain())
                .title(createNoticeRequest.title())
                .content(createNoticeRequest.content())
                .attachedFileName(attachedFileName)
                .visible(createNoticeRequest.visible())
                .visibleStartAt(createNoticeRequest.visibleStartAt())
                .visibleEndAt(createNoticeRequest.visibleEndAt())
                .build();
    }

    /**
     * UpdateNoticeRequest DTO를 기반으로 Notice 엔티티를 생성합니다.
     *
     * @param notice 수정할 Notice 엔티티
     * @param updateNoticeRequest 공지사항 수정에 필요한 데이터를 담은 DTO
     * @param attachedFileName 공지사항의 첨부 파일
     * @return 수정할 Notice 엔티티
     */
    public Notice toUpdateEntity(
            Notice notice,
            NoticeDto.UpdateNoticeRequest updateNoticeRequest,
            String attachedFileName
    ) {
        return Notice.builder()
                .id(notice.getId())
                .domain(Optional.ofNullable(updateNoticeRequest.domain()).orElse(notice.getDomain()))
                .title(Optional.ofNullable(updateNoticeRequest.title()).orElse(notice.getTitle()))
                .content(Optional.ofNullable(updateNoticeRequest.content()).orElse(notice.getContent()))
                .attachedFileName(Optional.ofNullable(attachedFileName).orElse(notice.getAttachedFileName()))
                .visible(Optional.ofNullable(updateNoticeRequest.visible()).orElse(notice.getVisible()))
                .visibleStartAt(Optional.ofNullable(updateNoticeRequest.visibleStartAt()).orElse(notice.getVisibleStartAt()))
                .visibleEndAt(Optional.ofNullable(updateNoticeRequest.visibleEndAt()).orElse(notice.getVisibleEndAt()))
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindNoticeRequest 객체를 기반으로
     * NoticeCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 공지사항 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 NoticeCriteria 객체
     */
    public NoticeCriteria toCriteria(NoticeDto.FindNoticeRequest findNoticeRequest) {
        return new NoticeCriteria(
                findNoticeRequest.noticeIds(),
                findNoticeRequest.domains(),
                findNoticeRequest.title(),
                findNoticeRequest.content(),
                findNoticeRequest.visible()
        );
    }

    /**
     * Notice 엔티티를 기반으로 응답용 NoticeResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param notice 변환할 Notice 엔티티 (null 가능)
     * @return Notice 엔티티의 데이터를 담은 NoticeResponse DTO, notice가 null이면 null 반환
     */
    public NoticeDto.NoticeResponse toResponseDto(Notice notice) {
        return Optional.ofNullable(notice)
                .map(n -> new NoticeDto.NoticeResponse(
                        n.getId(),
                        n.getDomain(),
                        n.getTitle(),
                        n.getContent(),
                        n.getAttachedFileName(),
                        n.getVisible(),
                        n.getVisibleStartAt(),
                        n.getVisibleEndAt(),
                        n.getCreatedAt(),
                        n.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Notice 엔티티 목록을 NoticePageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param noticePage Page 형태로 조회된 Notice 엔티티 목록 (null 가능)
     * @return Notice 엔티티 리스트와 페이지 정보를 담은 NoticePageResponse DTO, noticePage가 null이면 null 반환
     */
    public NoticeDto.NoticePageResponse toPageResponseDto(Page<Notice> noticePage) {
        return Optional.ofNullable(noticePage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new NoticeDto.NoticePageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}