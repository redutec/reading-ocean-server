package com.redutec.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Comment("독서능력진단평가 채점권")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ReadingDiagnosticTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("독서능력진단평가 채점권 고유번호")
    private Long id;

    @Comment("채점권이 소속한 바우처")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private ReadingDiagnosticVoucher readingDiagnosticVoucher;

    @Comment("채점권 일련번호(정확히 16자의 영문 대문자 및 숫자)")
    @Column(length = 16, nullable = false, unique = true)
    @Size(min = 16, max = 16, message = "채점권 일련번호는 16자의 영어 대문자와 숫자로만 구성됩니다")
    @Pattern(regexp = "^[A-Z0-9]{16}$", message = "채점권 일련번호는 정확히 16자의 영어 대문자와 숫자로만 구성되어야 합니다")
    private String serial;

    @Comment("사용 여부(false = 미사용, true = 사용됨)")
    @Column(nullable = false)
    @ColumnDefault("false")
    @Builder.Default
    private Boolean used = false;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * JPA 양방향 연관관계 설정을 위해 ReadingDiagnosticVoucher를
     * 이 티켓에 연결합니다.
     * 외부에서 직접 호출하지 않고, Voucher#addTicket()을 통해 연관시키는 것이 권장됩니다.
     *
     * @param readingDiagnosticVoucher 연관시킬 ReadingDiagnosticVoucher 엔티티
     */
    void setReadingDiagnosticVoucher(ReadingDiagnosticVoucher readingDiagnosticVoucher) {
        this.readingDiagnosticVoucher = readingDiagnosticVoucher;
    }

    /**
     * 이 채점권을 사용 처리하여 재사용을 불가능하게 설정합니다.
     * used 필드는 true로 변경되며, 이후 markAsUsed()는 다시 호출되지 않아야 합니다.
     */
    public void markAsUsed() {
        if (Boolean.TRUE.equals(this.used)) {
            throw new IllegalStateException("이미 사용한 채점권입니다.");
        }
        this.used = true;
    }
}