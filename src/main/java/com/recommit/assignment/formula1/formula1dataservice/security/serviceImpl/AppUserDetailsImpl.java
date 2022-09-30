package com.recommit.assignment.formula1.formula1dataservice.security.serviceImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recommit.assignment.formula1.formula1dataservice.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class AppUserDetailsImpl implements UserDetails {

    private final Long id;
    private final String username;
    @JsonIgnore
    private final String password;
    private final Boolean isEnabled;

    private final Collection<? extends GrantedAuthority> authorities;

    public AppUserDetailsImpl(UserEntity appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.password = appUser.getPassword();
        this.isEnabled = appUser.getIsEnabled();
        this.authorities = appUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
