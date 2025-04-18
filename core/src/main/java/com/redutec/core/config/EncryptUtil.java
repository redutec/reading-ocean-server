package com.redutec.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class EncryptUtil {
    private static final String ALGORITHM      = "AES";
    private static final String TRANSFORMATION = "AES";

    // Spring이 setKey() 호출 시 한 번만 초기화됩니다.
    private static SecretKey secretKey;

    /**
     * application.properties 혹은 application.yml의 aes-256-key 프로퍼티를
     * Base64 디코딩하여 SecretKey로 변환해 static 필드에 저장합니다.
     */
    @Value("${aes-256-key}")
    private void setKey(String base64Key) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
    }

    /**
     * AES 암호화 + Base64 인코딩
     */
    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error during encryption", e);
        }
    }

    /**
     * Base64 디코딩 + AES 복호화
     */
    public static String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            throw new RuntimeException("Error during decryption", e);
        }
    }
}