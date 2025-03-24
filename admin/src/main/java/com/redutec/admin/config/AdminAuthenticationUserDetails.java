package com.redutec.admin.config;

import com.redutec.core.entity.BotGroup;
import com.redutec.core.entity.BotGroupPermission;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class AdminAuthenticationUserDetails implements UserDetails {
    private Long userNo;
    private String userId;
    private String username;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    @Schema(description = "ROLE")
    private String role;

    @NotNull
    @Schema(description = "그룹")
    private BotGroup botGroup;

    @Schema(description = "그룹권한 리스트")
    private List<BotGroupPermission> botGroupPermission;

    private Collection<? extends GrantedAuthority> authorities;

    private String accessToken;
}