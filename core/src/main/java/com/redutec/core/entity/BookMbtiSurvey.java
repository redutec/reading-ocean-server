package com.redutec.core.entity;

import com.redutec.core.meta.BookMbtiResult;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Comment("북BTI 설문 응답")
@DynamicUpdate
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BookMbtiSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("북BTI 설문 응답 고유번호")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @Setter
    @Comment("응답자 학생")
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    @Comment("북BTI 결과")
    private BookMbtiResult result;

    @OneToMany(mappedBy = "bookMbtiSurvey", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @Setter
    @Comment("북BTI 설문 응답 상세내역")
    private List<BookMbtiSurveyAnswer> bookMbtiSurveyAnswers = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Comment("설문 응답 완료 일시")
    private LocalDateTime createdAt;
}