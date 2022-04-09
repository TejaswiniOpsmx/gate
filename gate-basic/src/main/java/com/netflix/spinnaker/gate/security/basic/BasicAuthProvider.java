/*
 * Copyright 2018 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.spinnaker.gate.security.basic;

import com.netflix.spinnaker.gate.services.OesAuthorizationService;
import com.netflix.spinnaker.gate.services.PermissionService;
import com.netflix.spinnaker.security.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Slf4j
public class BasicAuthProvider implements AuthenticationProvider {

  private final SecurityProperties securityProperties;

  private final PermissionService permissionService;
  private final OesAuthorizationService oesAuthorizationService;

  private List<String> roles;

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  private String name;
  private String password;

  public BasicAuthProvider(
      SecurityProperties securityProperties,
      PermissionService permissionService,
      OesAuthorizationService oesAuthorizationService) {
    this.securityProperties = securityProperties;
    this.permissionService = permissionService;
    this.oesAuthorizationService = oesAuthorizationService;
    if (securityProperties.getUser() == null) {
      throw new AuthenticationServiceException("User credentials are not configured");
    }
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String name = authentication.getName();
    String password =
        authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;

    log.info("submitted name and password is: {} , {}", name, password);
    log.info("configured name and password is: {} , {}", this.name, this.password);
    if (!this.name.equals(name) || !this.password.equals(password)) {
      throw new BadCredentialsException("Invalid username/password combination");
    }

    log.debug("roles configured for user: {} are roles: {}", name, roles);
    User user = new User();
    user.setEmail(name);
    user.setUsername(name);
    user.setRoles(Collections.singletonList("USER"));

    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    if (roles != null && !roles.isEmpty() && permissionService != null) {
      user.setRoles(roles);
      grantedAuthorities =
          roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
      permissionService.loginWithRoles(name, roles);
      log.info("Caching roles for user: {} with roles: {}", name, roles);
      oesAuthorizationService.cacheUserGroups(roles, name);
    } else {
      log.debug("If rbac is enabled at spinnaker then roles need to be configured for basic auth.");
    }

    return new UsernamePasswordAuthenticationToken(user, password, grantedAuthorities);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication == UsernamePasswordAuthenticationToken.class;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
