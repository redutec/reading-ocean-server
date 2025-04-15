package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BookFile 엔티티
 * 테이블 정보:
 * - 테이블 명: book_file
 * - 테이블 코멘트: 도서 첨부 파일 (독서기록장/파워북)
 */
@Entity
@Table(name = "book_file")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BookFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_file_no", nullable = false, updatable = false)
    private Integer bookFileNo;

    @Column(name = "book_no")
    private Integer bookNo;

    @Column(name = "file_type", length = 20, nullable = false)
    private String fileType;

    @Column(name = "file_name", length = 300, nullable = false)
    private String fileName;

    @Column(name = "file_path", length = 300, nullable = false)
    private String filePath;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}