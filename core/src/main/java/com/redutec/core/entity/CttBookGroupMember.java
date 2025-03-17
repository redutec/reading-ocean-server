package com.redutec.core.entity;

import com.redutec.core.entity.key.CttBookGroupMemberKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * CttBookGroupMember 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_book_group_member
 * - 테이블 코멘트: 도서 그룹 멤버
 */
@Entity
@Table(name = "ctt_book_group_member")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttBookGroupMember {
    @EmbeddedId
    private CttBookGroupMemberKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookGroupNo")
    @JoinColumn(name = "book_group_no", nullable = false)
    private CttBookGroup bookGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookNo")
    @JoinColumn(name = "book_no", nullable = false)
    private CttBook book;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    private Character useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", nullable = false, length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}