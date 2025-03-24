package com.redutec.core.config;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
import java.util.Random;

public class EncryptionUtil {
    private static final byte[] AES256_IV_BYTES = new byte[16]; // 16바이트 0 초기화
    private static final int SALT_SIZE = 16;

    /**
     * AES-256/CBC/PKCS5Padding 방식으로 문자열을 암호화합니다.
     *
     * @param str 평문 문자열
     * @param key 암호화 키 (최대 32자)
     * @return 암호화된 문자열(Base64 인코딩 후 특수문자 매핑 적용)
     */
    public static String aesEncode(String str, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (str == null || str.isEmpty()) {
            return "";
        }
        String aesKey = (key.length() > 32) ? key.substring(0, 32) : key;
        byte[] textBytes = str.getBytes(StandardCharsets.UTF_8);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(AES256_IV_BYTES);
        SecretKeySpec secretKey = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(textBytes);
        String base64Encoded = Base64.getEncoder().encodeToString(encryptedBytes);
        return charEnMapping(base64Encoded);
    }

    /**
     * AES-256/CBC/PKCS5Padding 방식으로 암호문을 복호화합니다.
     *
     * @param str 암호화된 문자열(Base64 인코딩 및 매핑 처리된 문자열)
     * @param key 복호화 키 (최대 32자)
     * @return 복호화된 평문 문자열
     */
    public static String aesDecode(String str, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (str == null || str.isEmpty()) {
            return "";
        }
        String mapped = charDeMapping(str);
        String aesKey = (key.length() > 32) ? key.substring(0, 32) : key;
        byte[] decodedBytes = Base64.getDecoder().decode(mapped.getBytes(StandardCharsets.UTF_8));
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(AES256_IV_BYTES);
        SecretKeySpec secretKey = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 암호화된 문자열에 대해 특수문자 매핑 처리를 합니다.
     */
    public static String charEnMapping(String input) {
        return input
                .replace("/", "_slh_")
                .replace("=", "_eq_")
                .replace(":", "_cl_")
                .replace(";", "_smc_")
                .replace("&", "_apr_")
                .replace("$", "_dl_")
                .replace("~", "_td_")
                .replace("!", "_exm_")
                .replace("@", "_at_")
                .replace("#", "_shp_")
                .replace("%", "_per_")
                .replace("*", "_ast_")
                .replace("\"", "_qut_")
                .replace("'", "_aps_")
                .replace("?", "_qut_") // 주의: "?"와 "\"" 모두 같은 매핑을 사용 중이라면 수정 필요
                .replace("+", "_p_")
                .replace("-", "_m_");
    }

    /**
     * 매핑 처리된 문자열을 원래대로 복원합니다.
     */
    public static String charDeMapping(String input) {
        return input
                .replace("_slh_", "/")
                .replace("_eq_", "=")
                .replace("_cl_", ":")
                .replace("_smc_", ";")
                .replace("_apr_", "&")
                .replace("_dl_", "$")
                .replace("_td_", "~")
                .replace("_exm_", "!")
                .replace("_at_", "@")
                .replace("_shp_", "#")
                .replace("_per_", "%")
                .replace("_ast_", "*")
                .replace("_qut_", "\"") // 주의: 매핑이 중복되는 경우 주의해야 합니다.
                .replace("_aps_", "'")
                .replace("_p_", "+")
                .replace("_m_", "-");
    }

    /**
     * SHA-256 해시 알고리즘을 사용하여 문자열을 암호화합니다.
     *
     * @param str 평문 문자열
     * @param key 추가 키 값 (암호화에 사용)
     * @return 16진수 문자열 형태의 해시 값
     */
    public static String password(String str, String key) throws NoSuchAlgorithmException {
        if (str == null || str.isEmpty()) {
            return "";
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String preKey = key.substring(0, Math.min(3, key.length()));
        String tailKey = (key.length() > 3) ? key.substring(3, Math.min(6, key.length())) : "";
        String temp = preKey + str + tailKey + key;
        byte[] digest = md.digest(temp.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digest);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    /**
     * 랜덤 salt 값을 생성합니다.
     *
     * @return 16바이트 salt를 16진수 문자열로 변환한 값
     */
    public static String getSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        sr.nextBytes(salt);
        return bytesToHex(salt);
    }

    /**
     * 지정한 크기의 숫자 키를 생성합니다.
     *
     * @param size 생성할 키의 길이
     * @return 숫자로 이루어진 문자열 키
     */
    public static String getNumberKey(int size) {
        StringBuilder rtnKey = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            rtnKey.append(random.nextInt(10));
        }
        return rtnKey.toString();
    }

    /**
     * 지정한 크기의 숫자와 영문 소문자가 섞인 문자열을 생성합니다.
     *
     * @param size 생성할 문자열의 길이
     * @return 생성된 문자열
     */
    public static String getRandmNumEng(int size) {
        StringBuilder buf = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < size; i++) {
            if (rnd.nextBoolean()) {
                buf.append((char) (rnd.nextInt(26) + 'a'));
            } else {
                buf.append(rnd.nextInt(10));
            }
        }
        return buf.toString();
    }

    /**
     * 주어진 문자열에 대해 SHA-256 해시를 계산합니다.
     *
     * @param text 평문 문자열
     * @return SHA-256 해시 값(16진수 문자열)
     */
    public static String sha256Encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(text.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digest);
    }
}