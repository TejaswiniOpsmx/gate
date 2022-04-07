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

import com.netflix.spinnaker.gate.services.PermissionService;
import com.netflix.spinnaker.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

@Slf4j
public class BasicAuthProvider implements AuthenticationProvider {

  private final SecurityProperties securityProperties;

  private final PermissionService permissionService;

  public BasicAuthProvider(SecurityProperties securityProperties, PermissionService permissionService) {
    this.securityProperties = securityProperties;
    this.permissionService = permissionService;

    if (securityProperties.getUser() == null) {
      throw new AuthenticationServiceException("User credentials are not configured");
    }
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String name = authentication.getName();
    String password =
        authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
    log.info("******************** user provided name is: {} and password is: {}" , name, password);
    log.info("******************** Configured name is: {} and password is: {}" , securityProperties.getUser().getName(), securityProperties.getUser().getPassword());
    if (!securityProperties.getUser().getName().equals(name)
        || !securityProperties.getUser().getPassword().equals(password)) {
      throw new BadCredentialsException("Invalid username/password combination");
    }



    User user = new User();
    user.setEmail(name);
    user.setUsername(name);
    user.setRoles(Arrays.asList("USER" , "ADMIN" , "ROLE_ADMIN" , "ROLE_USER"));
    SimpleGrantedAuthority user_auth = new SimpleGrantedAuthority("USER");
    SimpleGrantedAuthority role_user = new SimpleGrantedAuthority("ROLE_USER");
    SimpleGrantedAuthority role_admin = new SimpleGrantedAuthority("ROLE_ADMIN");
    SimpleGrantedAuthority admin_auth = new SimpleGrantedAuthority("ADMIN");

    permissionService.loginWithRoles(name, Arrays.asList("USER" , "ADMIN" , "ROLE_ADMIN" , "ROLE_USER"));
    return new UsernamePasswordAuthenticationToken(user, password, Arrays.asList(role_admin));
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication == UsernamePasswordAuthenticationToken.class;
  }
}

