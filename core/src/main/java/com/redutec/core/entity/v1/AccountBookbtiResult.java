package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * AccountBookbtiResult 엔티티
 * 테이블 정보:
 * - 테이블 명: account_bookbti_result
 * - 테이블 코멘트: 사용자 북BTI 진단 결과
 */
@Entity
@Table(name = "account_bookbti_result")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class AccountBookbtiResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_bookbti_result_no", nullable = false, updatable = false)
    private Integer accountBookbtiResultNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "bookbti_no", nullable = false)
    private Integer bookbtiNo;

    @Column(name = "bookbti_result_no", nullable = false)
    private Integer bookbtiResultNo;

    @Column(name = "school_grade", length = 20, nullable = false)
    private String schoolGrade;

    @Column(name = "percent_e", nullable = false)
    private Integer percentE;

    @Column(name = "percent_i", nullable = false)
    private Integer percentI;

    @Column(name = "percent_n", nullable = false)
    private Integer percentN;

    @Column(name = "percent_s", nullable = false)
    private Integer percentS;

    @Column(name = "percent_t", nullable = false)
    private Integer percentT;

    @Column(name = "percent_f", nullable = false)
    private Integer percentF;

    @Column(name = "percent_j", nullable = false)
    private Integer percentJ;

    @Column(name = "percent_p", nullable = false)
    private Integer percentP;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}