package com.redutec.admin.config;

import com.redutec.core.repository.BotUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * UserDetailsServiceImpl는 어드민 사용자 세부 정보를 로드하는 서비스 구현체입니다.
 * 이 서비스는 어드민 사용자 정보를 기반으로 Spring Security의 UserDetails 객체를 생성합니다.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final BotUserRepository botUserRepository;

    /**
     * 주어진 이메일을 사용하여 어드민 사용자 세부 정보를 로드합니다.
     *
     * @param userId 어드민 사용자의 로그인 아이디
     * @return UserDetails 객체로, 어드민 사용자 인증 및 권한 부여에 사용됩니다.
     * @throws UsernameNotFoundException 주어진 이메일을 가진 근로자가 데이터베이스에 존재하지 않을 경우 발생합니다.
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 암호화된 이메일로 어드민 사용자 정보 조회
        var botUser = botUserRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("BotUser not found with userId: " + userId));
        // 근로자의 역할을 GrantedAuthority로 변환하여 UserDetails 객체 생성
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 근로자의 권한을 설정하여 UserDetails 객체 반환
        return new org.springframework.security.core.userdetails.User(
                botUser.getUserId(),
                botUser.getPassword(),
                authorities);
    }
}