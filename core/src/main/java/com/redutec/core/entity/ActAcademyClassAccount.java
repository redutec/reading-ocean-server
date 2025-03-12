package com.redutec.core.entity;

import com.redutec.core.entity.key.ActAcademyClassAccountKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * ActAcademyClassAccount 엔티티
 * 테이블 정보:
 * - 테이블 명: act_academy_class_account
 * - 테이블 코멘트: 학급 계정
 */
@Entity
@Table(name = "act_academy_class_account")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ActAcademyClassAccount {
    @EmbeddedId
    private ActAcademyClassAccountKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("classNo")
    @JoinColumn(name = "class_no", nullable = false)
    private ActAcademyClass academyClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentAccountNo")
    @JoinColumn(name = "student_account_no", nullable = false)
    private ActAccount studentAccount;

    @Column(name = "use_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private Character useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}