package com.redutec.core.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

/**
 * AES-256 알고리즘을 사용하여 엔티티의 문자열을 암호화/복호화하는 JPA AttributeConverter.
 */
@Component
@Converter
public class AesAttributeConverter implements AttributeConverter<String, String> {
    private final EncryptUtil encryptUtil;

    /**
     * 생성자 주입을 통해 EncryptUtil 빈을 주입받습니다.
     * @param encryptUtil AES 암호화/복호화 유틸리티
     */
    public AesAttributeConverter(
            EncryptUtil encryptUtil
    ) {
        this.encryptUtil = encryptUtil;
    }

    /**
     * 엔티티의 속성값을 DB에 저장하기 전 AES-256으로 암호화하고 Base64로 인코딩합니다.
     * @param attribute 엔티티 속성(평문)
     * @return 암호화된 Base64 문자열 또는 null
     */
    @Override
    public String convertToDatabaseColumn(
            String attribute
    ) {
        return (attribute == null) ? null : encryptUtil.encrypt(attribute);
    }

    /**
     * DB에서 읽어온 값을 Base64 디코딩 후 AES-256 복호화하여 엔티티에 설정합니다.
     * @param dbData 암호화된 Base64 문자열
     * @return 복호화된 평문 문자열 또는 null
     */
    @Override
    public String convertToEntityAttribute(
            String dbData
    ) {
        return (dbData == null) ? null : encryptUtil.decrypt(dbData);
    }
}