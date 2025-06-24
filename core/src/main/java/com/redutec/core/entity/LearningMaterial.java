package com.redutec.core.entity;

import com.redutec.core.meta.LearningMaterialAuthor;
import com.redutec.core.meta.LearningMaterialCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Comment("학습 자료")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LearningMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("학습 자료 고유번호")
    private Long id;

    @Comment("학습 자료 분류(독서목록/교육자료/홍보자료/상담자료/기타)")
    @Setter
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LearningMaterialCategory category;

    @Comment("제목")
    @Setter
    @Column(nullable = false, length = 100)
    @NotBlank
    private String title;

    @Comment("내용")
    @Setter
    @Column(nullable = false)
    @Lob
    private String content;

    @Comment("작성자")
    @Setter
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LearningMaterialAuthor author;

    @Comment("첨부파일 목록(파일명)")
    @Setter
    @ElementCollection
    @CollectionTable(
        name = "learning_material_attachment",
        joinColumns = @JoinColumn(name = "learning_material_id")
    )
    @Column(name = "attachment_file_name", length = 100)
    private List<String> attachmentFileNames;

    @Comment("활성화 여부")
    @Setter
    @Column(nullable = false)
    @Builder.Default
    private Boolean available = true;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}