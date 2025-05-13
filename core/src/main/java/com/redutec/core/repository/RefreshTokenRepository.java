package com.redutec.core.repository;

import com.redutec.core.entity.RefreshToken;
import com.redutec.core.meta.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * RefreshToken 엔티티에 대한 데이터 접근을 제공하는 리포지토리 인터페이스입니다.
 * 이 인터페이스는 JPA의 기본 CRUD 기능과 스펙을 통한 쿼리 실행을 지원합니다.
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>, JpaSpecificationExecutor<RefreshToken> {
    /**
     * Token을 이용해 Refresh Token을 조회합니다.
     *
     * @param token 저장된 Refresh Token
     * @return 해당 Refresh Token의 Optional 객체
     */
    Optional<RefreshToken> findByToken(String token);
    /**
     * 특정 사용자의 모든 Refresh Token을 삭제합니다.
     *
     * @param username 사용자 아이디
     * @param domain 서비스 도메인
     */
    void deleteByUsernameAndDomain(String username, Domain domain);
}