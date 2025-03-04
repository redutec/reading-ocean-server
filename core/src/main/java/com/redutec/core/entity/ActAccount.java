package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "act_account", indexes = {
        @Index(name = "idx_act_account_academy_no", columnList = "academy_no"),
        @Index(name = "idx_act_account_signup_domain_code", columnList = "signup_domain_code")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "account_ID", length = 16, nullable = false, unique = true)
    private String accountId;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "password_salt", length = 45, nullable = false)
    private String passwordSalt;

    @Column(name = "account_status", length = 20)
    private String accountStatus;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "address_detail", length = 200)
    private String addressDetail;

    @Column(name = "zip_code", length = 10)
    private String zipCode;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "gender", nullable = false)
    private Byte gender;

    @Column(name = "mobile_no", length = 15, nullable = false)
    private String mobileNo;

    @Column(name = "academy_no")
    private Integer academyNo;

    @Column(name = "allow_sms_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private Character allowSmsYn;

    @Column(name = "allow_email_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private Character allowEmailYn;

    @Column(name = "profile_image_path", length = 200)
    private String profileImagePath;

    @Column(name = "school_name", length = 20)
    private String schoolName;

    @Column(name = "school_grade", length = 15, nullable = false)
    private String schoolGrade;

    @Column(name = "protector_mobile_no", length = 15)
    private String protectorMobileNo;

    @Column(name = "raq")
    private Short raq;

    @Column(name = "reading_type", length = 10)
    private String readingType;

    @Column(name = "personality_type", length = 10)
    private String personalityType;

    @Column(name = "bookbti_diagnosis_datetime")
    private LocalDateTime bookbtiDiagnosisDatetime;

    @Column(name = "reading_level", length = 10)
    private String readingLevel;

    @Column(name = "reading_diagnosis_datetime")
    private LocalDateTime readingDiagnosisDatetime;

    @Column(name = "book_point", columnDefinition = "INT DEFAULT 0")
    private Integer bookPoint;

    @Column(name = "game_reset_datetime")
    private LocalDateTime gameResetDatetime;

    @Column(name = "this_month_goal_book_count", columnDefinition = "SMALLINT DEFAULT 0")
    private Short thisMonthGoalBookCount;

    @Column(name = "this_month_verification_book_count", columnDefinition = "SMALLINT DEFAULT 0")
    private Short thisMonthVerificationBookCount;

    @Column(name = "this_month_book_point", columnDefinition = "INT DEFAULT 0")
    private Integer thisMonthBookPoint;

    @Column(name = "is_test_account", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isTestAccount;

    @Column(name = "signup_domain_code", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'READING_OCEAN_EDU'")
    private String signupDomainCode;

    @Column(name = "quit_datetime")
    private LocalDateTime quitDatetime;

    @Column(name = "quit_reason", length = 50)
    private String quitReason;

    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    @CreatedDate
    private LocalDateTime registerDatetime;

    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT NOW() ON UPDATE CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "last_login_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastLoginDatetime;

    @Column(name = "change_password_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime changePasswordDatetime;
}