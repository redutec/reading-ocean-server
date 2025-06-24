package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Comment("학급")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Homeroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("학급 고유번호")
    private Long id;

    @Comment("학급명")
    @Setter
    @Column(nullable = false, length = 20)
    private String name;

    @Comment("소속 교육기관")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Institute institute;

    @Comment("비고")
    @Setter
    @Column
    private String description;

    @Comment("이 학급에 소속된 학생")
    @Setter
    @OneToMany(mappedBy = "homeroom", fetch = FetchType.LAZY)
    private List<Student> students;

    @Comment("이 학급에 소속된 교사")
    @Setter
    @OneToMany(mappedBy = "homeroom", fetch = FetchType.LAZY)
    private List<Teacher> teachers;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}