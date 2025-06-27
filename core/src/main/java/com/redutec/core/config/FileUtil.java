package com.redutec.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {
    // application.yml에 설정된 file.prefix (예: "./upload")
    @Value("${file.prefix}")
    private String PATH_PREFIX;

    // 파일 접근 URL의 base URL (예: http://localhost:28080)
    @Value("${file.baseUrl}")
    private String BASE_URL;

    /**
     * 파일 업로드 메서드
     *
     * @param file    업로드할 파일
     * @param addPath 추가 경로 (예: "/banner/pc")
     * @return 업로드된 파일의 로컬 저장 경로와 접근 URL을 담은 FileUploadResult 객체
     */
    public FileUploadResult uploadFile(MultipartFile file, String addPath) {
        // 추가 경로 뒤에 "/" 제거
        if (addPath.endsWith("/")) {
            addPath = addPath.substring(0, addPath.length() - 1);
        }
        // 원본 파일 이름 추출 및 유효성 검사
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 파일 이름입니다.");
        }
        // 확장자 추출 (마지막 '.' 이후)
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < originalFilename.length() - 1) {
            extension = originalFilename.substring(dotIndex + 1);
        }
        // 새로운 파일 이름 생성 (UUID 기반)
        String newFileName = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
        // 저장 경로 생성
        // 프로젝트 루트(System.getProperty("user.dir"))를 기준으로 PATH_PREFIX와 addPath를 결합
        Path destDirectory = Paths.get(System.getProperty("user.dir"), PATH_PREFIX, addPath);
        try {
            Files.createDirectories(destDirectory);
        } catch (IOException e) {
            log.error("디렉토리 생성 실패: {}", destDirectory, e);
            throw new RuntimeException("파일 저장 경로 생성 실패", e);
        }
        // 저장할 파일의 전체 경로
        Path destPath = destDirectory.resolve(newFileName);
        try {
            file.transferTo(destPath.toFile());
        } catch (IOException e) {
            log.error("파일 업로드 실패: {}", e.getMessage(), e);
            throw new RuntimeException("파일 업로드 실패", e);
        }
        // BASE_URL, addPath, 새 파일 이름을 조합하여 접근 가능한 URL 생성
        String fileUrl = BASE_URL + addPath + "/" + newFileName;
        return new FileUploadResult(destPath.toString(), fileUrl);
    }

    /**
     * 업로드된 파일의 크기와 확장자를 검증합니다.
     *
     * @param file              검증할 파일
     * @param maxFileSize       최대 허용 파일 크기 (바이트 단위)
     * @param allowedExtensions 허용 확장자 배열 (예: {"jpg", "png", "gif"})
     */
    public void validateFile(MultipartFile file, long maxFileSize, String[] allowedExtensions) {
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("파일 크기가 허용 범위를 초과합니다.");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 파일 이름입니다.");
        }
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < originalFilename.length() - 1) {
            extension = originalFilename.substring(dotIndex + 1);
        }
        boolean allowed = false;
        for (String ext : allowedExtensions) {
            if (extension.equalsIgnoreCase(ext)) {
                allowed = true;
                break;
            }
        }
        if (!allowed) {
            throw new IllegalArgumentException("허용되지 않는 파일 확장자입니다.");
        }
    }

    /**
     * 파일 삭제 메서드
     *
     * @param addPath  업로드 시 사용한 추가 경로 (예: "/learning-material")
     * @param fileName 삭제할 파일명
     */
    public void deleteFile(String addPath, String fileName) {
        // addPath 끝의 모든 슬래시를 제거
        var normalizedAddPath = addPath.replaceAll("/+$", "");
        // 실제 파일 시스템 경로 구성
        var filePath = Paths.get(
                System.getProperty("user.dir"),
                PATH_PREFIX,
                normalizedAddPath,
                fileName
        );
        try {
            // 파일 삭제 시도 후 성공 여부를 리턴
            var deleted = Files.deleteIfExists(filePath);
            // 성공한 경우 로깅
            Optional.of(deleted)
                    .filter(Boolean::booleanValue)
                    .ifPresent(b -> log.info("파일 삭제 성공: {}", filePath));
            // 파일이 없어서 삭제되지 않은 경우 로깅
            Optional.of(deleted)
                    .filter(d -> !d)
                    .ifPresent(d -> log.warn("삭제할 파일이 존재하지 않습니다: {}", filePath));
        } catch (IOException e) {
            log.error("파일 삭제 실패: {}", filePath, e);
            throw new RuntimeException("파일 삭제 실패", e);
        }
    }
}