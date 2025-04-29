package com.redutec.core.entity;

import com.redutec.core.meta.BookGroupType;
import com.redutec.core.meta.SchoolGrade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Comment("도서 그룹")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_group_year_month_type_grade",
                        columnNames = {"year_month", "group_type", "school_grade"})
        }
)
public class BookGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("도서 그룹 고유번호")
    private Long id;

    @Comment("기준연월")
    @Column(nullable = false)
    private YearMonth yearMonth;

    @Comment("그룹 유형")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookGroupType type;

    @Comment("대상 학년(그룹 유형이 GRADE_LINK일 때만 존재)")
    @Column
    @Enumerated(EnumType.STRING)
    private SchoolGrade schoolGrade;

    @Comment("그룹에 포함된 도서")
    @ManyToMany
    @JoinTable(
            name = "book_group_items",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    // 도서 추가/제거 편의 메서드
    public void addBook(Book book) {
        if (!books.contains(book)) books.add(book);
    }
    public void removeBook(Book book) {
        books.remove(book);
    }

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateBookGroup(
            YearMonth yearMonth,
            BookGroupType type,
            SchoolGrade schoolGrade
    ) {
        // 1) type 우선 업데이트
        BookGroupType updatedType = Optional.ofNullable(type).orElse(this.type);
        this.type = updatedType;
        // 2) GRADE_LINK일 때는 requireNonNull, 아니면 기존 또는 새 값으로 대체
        this.schoolGrade = updatedType == BookGroupType.GRADE_LINK
                ? Objects.requireNonNull(schoolGrade, "GRADE_LINK 그룹은 schoolGrade가 필수입니다.")
                : Optional.ofNullable(schoolGrade).orElse(this.schoolGrade);
        // 3) yearMonth 업데이트
        this.yearMonth = Optional.ofNullable(yearMonth).orElse(this.yearMonth);
    }
}