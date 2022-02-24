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
 *
 */

package com.opsmx.spinnaker.gate.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@Component
public class RBACFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(req);
    try {
      logRequestBody(wrappedRequest);
      chain.doFilter(wrappedRequest, response);
    } finally {

    }
  }

  private static void logRequestBody(ContentCachingRequestWrapper request) {

    byte[] buf = request.getContentAsByteArray();
    if (buf.length > 0) {
      try {
        String requestBody = new String(buf, 0, buf.length, request.getCharacterEncoding());
        log.info("request body in RBAC filter : {}", requestBody);
      } catch (Exception e) {
        log.error("error in reading request body : {}", e);
      }
    }
  }
}
