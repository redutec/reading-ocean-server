package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * RankAggregate 엔티티
 * 테이블 정보:
 * - 테이블 명: rank_aggregate
 * - 테이블 코멘트: 집계 번호 등 집계 정보
 */
@Entity
@Table(name = "rank_aggregate", indexes = {
        @Index(name = "idx_rank_aggregate", columnList = "academy_no, aggregation_date")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RankAggregate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_aggregate_no", nullable = false, updatable = false)
    private Integer rankAggregateNo;

    @Column(name = "account_no")
    private Integer accountNo;

    @Column(name = "account_ID", length = 16)
    private String accountID;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "academy_no")
    private Integer academyNo;

    @Column(name = "academy_name", length = 30)
    private String academyName;

    @Column(name = "book_count", nullable = false)
    private Integer bookCount;

    @Column(name = "book_point", nullable = false)
    private Integer bookPoint;

    @Column(name = "school_grade", length = 15)
    private String schoolGrade;

    @Column(name = "profile_image_path", length = 200)
    private String profileImagePath;

    @Column(name = "aggregation_date", length = 10, nullable = false)
    private String aggregationDate;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}