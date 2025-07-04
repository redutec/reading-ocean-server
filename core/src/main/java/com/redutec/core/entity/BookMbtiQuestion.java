package com.redutec.core.entity;

import com.redutec.core.meta.BookMbtiQuestionType;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Comment("북BTI 설문 문항")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookMbtiQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("북BTI 설문 문항 고유번호")
    private Long id;

    @Comment("문항 유형")
    @Setter
    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private BookMbtiQuestionType type;

    @Comment("문항")
    @Setter
    @Column(nullable = false)
    @NotBlank
    private String question;

    @OneToMany(
            mappedBy = "bookMbtiQuestion",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Setter
    @Builder.Default
    @Comment("문항별 선택지 목록")
    private List<BookMbtiQuestionChoice> choices = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}