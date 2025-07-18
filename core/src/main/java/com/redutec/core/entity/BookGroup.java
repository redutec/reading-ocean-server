package com.redutec.core.entity;

import com.redutec.core.meta.BookGroupType;
import com.redutec.core.meta.SchoolGrade;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
@Comment("도서 그룹")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_group_year_month_type_grade",
        columnNames = {"year_month", "group_type", "school_grade"})
})
public class BookGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("도서 그룹 고유번호")
    private Long id;

    @Comment("그룹명")
    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    @Comment("기준연월")
    @Setter
    @Column(nullable = false)
    @Pattern(regexp  = "^[0-9]{4}-[0-9]{2}$", message = "기준연월은 YYYY-MM 형식이어야 합니다.")
    private String yearMonth;

    @Comment("그룹 유형")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookGroupType type;

    @Comment("대상 학년(그룹 유형이 GRADE_LINK일 때만 존재)")
    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private SchoolGrade schoolGrade;

    @Comment("그룹에 포함된 도서")
    @Setter
    @ManyToMany
    @JoinTable(
            name = "book_group_items",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}