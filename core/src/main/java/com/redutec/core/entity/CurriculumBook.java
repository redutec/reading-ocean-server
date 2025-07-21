package com.redutec.core.entity;

import com.redutec.core.meta.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Comment("커리큘럼에 소속할 도서 정보")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CurriculumBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("커리큘럼에 소속할 도서 정보 고유번호")
    private Long id;

    @Comment("소속한 커리큘럼")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Curriculum curriculum;

    @Comment("읽어야 할 도서")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Book book;

    @Comment("독서 순서")
    @Setter
    @Column(nullable = false)
    private Integer readingOrder;

    @Comment("독서 예정일")
    @Setter
    @Column
    private LocalDate plannedReadingDate;

    @Comment("독서 상태")
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ReadingStatus readingStatus = ReadingStatus.PENDING;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}