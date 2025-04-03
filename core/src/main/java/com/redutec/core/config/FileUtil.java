package com.redutec.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {
    @Value("${file.prefix}")
    private String PATH_PREFIX;

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
        // 저장 경로 생성 (PATH_PREFIX와 addPath 결합)
        Path destDirectory = Paths.get(PATH_PREFIX, addPath);
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
}