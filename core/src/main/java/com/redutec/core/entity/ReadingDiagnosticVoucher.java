package com.redutec.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Comment("독서능력진단평가 바우처")
@Getter
@Builder
@AllArgsConstructor
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ReadingDiagnosticVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("바우처 고유번호")
    private Long id;

    @Comment("바우처 이름(사용자에게 보여질 이름)")
    @Setter
    @Column(length = 40, nullable = false, unique = true)
    @Size(max = 40, message = "바우처명은 최대 40자까지 입력할 수 있습니다")
    private String name;

    @Comment("바우처 코드(영문 대문자와 숫자만 허용)")
    @Column(length = 8, nullable = false, unique = true)
    @Size(max = 8, message = "바우처 코드는 최대 8자까지 입력할 수 있습니다")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "바우처 코드는 영어 대문자와 숫자만 사용할 수 있습니다")
    private String code;

    @Comment("바우처를 사용하는 교육기관")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Institute institute;

    @Comment("발급한 채점권 목록")
    @OneToMany(mappedBy = "readingDiagnosticVoucher", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ReadingDiagnosticTicket> readingDiagnosticTickets = new ArrayList<>();

    @Comment("비고")
    @Setter
    @Column(nullable = false)
    private String description;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 지정된 티켓을 본 바우처에 연관시키고,
     * ReadingDiagnosticTicket#voucher 필드를 함께 설정하여
     * 양방향 연관관계를 완성합니다.
     *
     * @param readingDiagnosticTicket 추가할 ReadingDiagnosticTicket 엔티티
     */
    public void addReadingDiagnosticTicket(ReadingDiagnosticTicket readingDiagnosticTicket) {
        readingDiagnosticTickets.add(readingDiagnosticTicket);
        readingDiagnosticTicket.setReadingDiagnosticVoucher(this);
    }
}