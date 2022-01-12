/*
 * Copyright 2022 OpsMx, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package com.opsmx.spinnaker.gate.interceptors;

import com.opsmx.spinnaker.gate.rbac.ApplicationFeatureRbac;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class RbacInterceptor implements HandlerInterceptor {

  @Autowired private ApplicationFeatureRbac applicationFeatureRbac;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    log.info("request intercepted");
    log.info("Cookie headers : {}", request.getHeader("Cookie"));
    applicationFeatureRbac.authorizeUser(
        request.getUserPrincipal().getName(), request.getRequestURI(), request.getMethod());
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

    log.info("post handle : {}", new String(responseWrapper.getContentAsByteArray()));
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

    log.info("afterCompletion : {}", new String(responseWrapper.getContentAsByteArray()));
  }
}
