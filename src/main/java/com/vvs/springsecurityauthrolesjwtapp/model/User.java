package com.vvs.springsecurityauthrolesjwtapp.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static java.util.Collections.singletonList;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@Document("users")
public class User implements UserDetails {

  @Id
  private String id;
  private String username;
  private String password;
  private String email;

  private String firstName;
  private String lastName;
  private String phone;
  private Address address;

  private String avatar;

  private Date onCreate;
  private Date onUpdate;
  private Boolean isActive;
  private Role role;

public User() {
  onCreate = Date.from(Instant.now());
  onUpdate = onCreate;
  role = Role.USER;
  isActive = true;
}

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return singletonList(new SimpleGrantedAuthority(role.name()));
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
    return isActive;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isActive;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isActive;
  }

  @Override
  public boolean isEnabled() {
    return isActive;
  }

}
