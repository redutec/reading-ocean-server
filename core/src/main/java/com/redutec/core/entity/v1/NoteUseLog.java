package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * NoteUseLog 엔티티
 * 테이블 정보:
 * - 테이블 명: note_use_log
 * - 테이블 코멘트: 독서노트 작성 로그
 */
@Entity
@Table(name = "note_use_log")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class NoteUseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_use_log_no", nullable = false, updatable = false)
    private Integer noteUseLogNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "status", length = 20)
    private String status;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @Column(name = "subscription_no")
    private Integer subscriptionNo;
}