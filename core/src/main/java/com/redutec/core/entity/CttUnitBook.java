package com.redutec.core.entity;

import com.redutec.core.entity.key.CttUnitBookKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * CttUnitBook 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_unit_book
 * - 테이블 코멘트: 연계 교과 단원 도서
 */
@Entity
@Table(name = "ctt_unit_book")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttUnitBook {
    @EmbeddedId
    private CttUnitBookKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookNo")
    @JoinColumn(name = "book_no", nullable = false)
    private CttBook book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("linkUnitNo")
    @JoinColumn(name = "link_unit_no", nullable = false)
    private CttUnit unit;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1) default 'Y'")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", nullable = false, length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}