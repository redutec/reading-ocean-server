package com.redutec.core.entity;

import com.redutec.core.meta.CurriculumStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Comment("독서교육 커리큘럼")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("커리큘럼 고유번호")
    private Long id;

    @Comment("커리큘럼을 진행하는 학생")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;

    @Comment("커리큘럼명")
    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    @Comment("설명")
    @Setter
    @Column
    private String description;

    @Comment("시작일")
    @Setter
    @Column(nullable = false)
    private LocalDate startDate;

    @Comment("종료일")
    @Setter
    @Column(nullable = false)
    private LocalDate endDate;

    @Comment("상태")
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CurriculumStatus status = CurriculumStatus.PENDING;

    @Comment("커리큘럼에 소속된 도서 정보")
    @OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurriculumBook> curriculumBooks = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}