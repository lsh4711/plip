package com.server.global.auth.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.server.domain.member.entity.Member;

import lombok.Getter;

@Getter
public final class MemberDetails extends Member implements UserDetails {
    public MemberDetails(Member member) {
        super(member.getMemberId(),
            member.getEmail(),
            member.getPassword(),
            member.getPassword(),
            member.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] roles = getRole().getRoles();

        return AuthorityUtils.createAuthorityList(roles);
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
