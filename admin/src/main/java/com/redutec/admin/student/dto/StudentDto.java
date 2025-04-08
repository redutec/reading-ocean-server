package com.redutec.admin.student.dto;

import com.redutec.core.meta.AccountStatus;
import com.redutec.core.meta.Domain;
import com.redutec.core.meta.SchoolGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class StudentDto {
    @Schema(description = "계정 등록 요청 객체")
    record CreateStudentRequest(
        @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "^(?=.{6,16}$)(?=.*[a-z])[a-z0-9]+$", message = "accountId는 6자 이상 16자 이하의 영문 소문자와 숫자 조합이어야 하며, 순수 숫자는 허용되지 않습니다.")
        String accountId,

        @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(
                regexp = "^(?=.{8,16}$)(?:(?=.*[A-Za-z])(?=.*\\d)|(?=.*[A-Za-z])(?=.*[^A-Za-z\\d])|(?=.*\\d)(?=.*[^A-Za-z\\d])).*$",
                message = "비밀번호는 8~16자 영문/숫자/특수문자 중 2개 이상 조합이어야 합니다."
        )
        String password,

        @Schema(description = "계정 상태", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Enumerated(EnumType.STRING)
        AccountStatus accountStatus,

        @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Email
        String email,

        @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(max = 20)
        String name,

        @Schema(description = "생년월일", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        LocalDate birthday,

        @Schema(description = "성별", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Min(1)
        @Max(2)
        Byte gender,

        @Schema(description = "연락처", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리 숫자여야 합니다.")
        String mobileNo,

        @Schema(description = "학교명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String schoolName,

        @Schema(description = "학년", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Enumerated(EnumType.STRING)
        SchoolGrade schoolGrade,

        @Schema(description = "주소", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 200)
        String address,

        @Schema(description = "상세 주소", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 200)
        String addressDetail,

        @Schema(description = "보호자 연락처", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리 숫자여야 합니다.")
        String protectorMobileNo,

        @Schema(description = "가입 도메인", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Enumerated(EnumType.STRING)
        Domain signupDomainCode,

        @Schema(description = "이메일 수신 허용 여부", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "이메일 수신 허용 여부는 Y 또는 N이어야 합니다.")
        String allowEmailYn,

        @Schema(description = "SMS 수신 허용 여부", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "SMS 수신 허용 여부는 Y 또는 N이어야 합니다.")
        String allowSmsYn,

        @Schema(description = "소속 학원의 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotBlank
        Integer academyNo
    ) {}

    @Schema(description = "학생 계정 조회 요청 리코드")
    record FindStudentRequest(
        @Schema(description = "학원명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 30)
        String academyName,

        @Schema(description = "학생명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 20)
        String name,

        @Schema(description = "학생 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 16)
        String accountId,

        @Schema(description = "가입 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Enumerated(EnumType.STRING)
        Domain signupDomainCode
    ) {}

    @Schema(description = "학생 계정 수정 요청 리코드")
    record UpdateStudentRequest(
            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            @Pattern(regexp = "^(?=.{6,16}$)(?=.*[a-z])[a-z0-9]+$", message = "accountId는 6자 이상 16자 이하의 영문 소문자와 숫자 조합이어야 하며, 순수 숫자는 허용되지 않습니다.")
            String accountId,

            @Schema(description = "새로운 비밀번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            @Pattern(
                    regexp = "^(?=.{8,16}$)(?:(?=.*[A-Za-z])(?=.*\\d)|(?=.*[A-Za-z])(?=.*[^A-Za-z\\d])|(?=.*\\d)(?=.*[^A-Za-z\\d])).*$",
                    message = "비밀번호는 8~16자 영문/숫자/특수문자 중 2개 이상 조합이어야 합니다."
            )
            String newPassword,

            @Schema(description = "계정 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            @Enumerated(EnumType.STRING)
            AccountStatus accountStatus,

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            @Email
            String email,

            @Schema(description = "이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            @Size(max = 20)
            String name,

            @Schema(description = "생년월일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            LocalDate birthday,

            @Schema(description = "성별", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Min(1)
            @Max(2)
            Byte gender,

            @Schema(description = "연락처", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리 숫자여야 합니다.")
            String mobileNo,

            @Schema(description = "학교명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String schoolName,

            @Schema(description = "학년", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            @Enumerated(EnumType.STRING)
            SchoolGrade schoolGrade,

            @Schema(description = "주소", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String address,

            @Schema(description = "상세 주소", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String addressDetail,

            @Schema(description = "보호자 연락처", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리 숫자여야 합니다.")
            String protectorMobileNo,

            @Schema(description = "이메일 수신 허용 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            @Pattern(regexp = "^[YN]$", message = "이메일 수신 허용 여부는 Y 또는 N이어야 합니다.")
            String allowEmailYn,

            @Schema(description = "SMS 수신 허용 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            @Pattern(regexp = "^[YN]$", message = "SMS 수신 허용 여부는 Y 또는 N이어야 합니다.")
            String allowSmsYn,

            @Schema(description = "소속 학원의 고유번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
            @NotBlank
            Integer academyNo
    ) {}

    @Schema(description = "학생 계정 조회 응답 객체")
    record StudentResponse(
        Integer accountNo,
        String accountId,
        String email,
        String name,
        String mobileNo,
        SchoolGrade schoolGrade,
        AccountStatus accountStatus,
        Domain signupDomainCode,
        Integer academyNo,
        String academyName
    ) {}
}