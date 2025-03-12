package com.redutec.core.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AcademySubscriptionBillDetailUserHistoryKey implements Serializable {
    @Column(name = "academy_subscription_bill_detail_history_no", nullable = false)
    private Integer academySubscriptionBillDetailHistoryNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;
}