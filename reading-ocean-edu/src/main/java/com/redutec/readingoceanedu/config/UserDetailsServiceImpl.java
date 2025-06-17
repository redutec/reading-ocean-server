package com.redutec.readingoceanedu.config;

import com.redutec.core.meta.AuthenticationStatus;
import com.redutec.core.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserDetailsServiceImpl는 학생 세부 정보를 로드하는 서비스 구현체입니다.
 * 이 서비스는 학생 정보를 기반으로 Spring Security의 UserDetails 객체를 생성합니다.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final StudentRepository studentRepository;

    /**
     * 주어진 로그인 아이디를 사용하여 학생의 세부 정보를 로드합니다.
     *
     * @param accountId 학생의 로그인 아이디
     * @return UserDetails 객체로, 학생 인증 및 권한 부여에 사용됩니다.
     * @throws UsernameNotFoundException 주어진 이메일을 가진 학생가 데이터베이스에 존재하지 않을 경우 발생합니다.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        // 로그인 아이디로 학생 정보 조회
        var student = studentRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 학생입니다. accountId: " + accountId));
        // 학생 권한을 설정하여 UserDetails 객체 반환
        return User.builder()
                .username(student.getAccountId())
                .password(student.getPassword())
                .accountLocked(student.getAuthenticationStatus() == AuthenticationStatus.LOCKED)
                .disabled(student.getAuthenticationStatus() == AuthenticationStatus.SUSPENDED)
                .build();
    }
}