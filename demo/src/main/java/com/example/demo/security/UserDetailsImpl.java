package com.example.demo.security;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class UserDetailsImpl implements UserDetails {
    private User user;
    private Collection<GrantedAuthority> authorities;
    public int getCramSchoolId(){
        return user.getCramSchool().getCramSchoolId();
    }

    public CramSchool getCramSchool(){
        return user.getCramSchool();
    }

    public String getCramSchoolName(){
        return user.getCramSchool().getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
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
