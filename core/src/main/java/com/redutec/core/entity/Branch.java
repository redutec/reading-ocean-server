package com.redutec.core.entity;

import com.redutec.core.meta.BranchStatus;
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
@Comment("지사")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("지사 고유번호")
    private Long id;

    @Comment("지사장(교사 엔티티)")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Teacher managerTeacher;

    @Comment("권역")
    @Column(length = 10)
    private String region;

    @Comment("지사명")
    @Column(length = 20, nullable = false)
    private String name;

    @Comment("상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BranchStatus status;

    @Comment("영업 구역")
    @Column(length = 200)
    private String businessArea;

    @Comment("계약서 파일명")
    @Column
    private String contractFileName;

    @Comment("계약일")
    @Column
    private LocalDate contractDate;

    @Comment("갱신일")
    @Column
    private LocalDate renewalDate;

    @Comment("비고")
    @Column
    private String description;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}