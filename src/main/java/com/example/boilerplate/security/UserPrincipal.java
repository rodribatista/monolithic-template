package com.example.boilerplate.security;

import com.example.boilerplate.models.PrivilegeEntity;
import com.example.boilerplate.models.RoleEntity;
import com.example.boilerplate.models.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
public class UserPrincipal implements UserDetails {

  private final String id;
  private final String email;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  private UserPrincipal(String id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal create(UserEntity user) {
    return new UserPrincipal(
      user.getId(),
      user.getEmail(),
      user.getPassword(),
      getGrantedAuthorities(getPrivileges(user.getRoles()))
    );
  }

  @Override
  public String getUsername() {
    return email;
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

  private static Set<GrantedAuthority> getGrantedAuthorities(Set<String> authorities) {
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    if (authorities != null) {
      for (String authoritie : authorities) {
        grantedAuthorities.add(new SimpleGrantedAuthority(authoritie));
      }
    }
    return grantedAuthorities;
  }

  private static Set<String> getPrivileges(Set<RoleEntity> roles) {
    Set<String> grantedAuthorities = new HashSet<>();
    if (roles != null) {
      for (RoleEntity roleEntity : roles) {
        Set<PrivilegeEntity> privileges = roleEntity.getPrivileges();
        if (privileges != null) {
          for (PrivilegeEntity privilege : privileges) {
            grantedAuthorities.add(privilege.getName());
          }
        }
      }
    }
    return grantedAuthorities;
  }

}