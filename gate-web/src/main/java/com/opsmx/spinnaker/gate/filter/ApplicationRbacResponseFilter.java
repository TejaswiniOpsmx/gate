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

package com.opsmx.spinnaker.gate.filter;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Order(1)
@Component
public class ApplicationRbacResponseFilter implements Filter {

  private static Gson gson = new Gson();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    ContentCachingResponseWrapper responseWrapper =
        new ContentCachingResponseWrapper((HttpServletResponse) response);

    chain.doFilter(request, responseWrapper);
    String content = new String(responseWrapper.getContentAsByteArray());
    log.info("response in ApplicationRbacResponseFilter : {}", content);
    //    Map<String, String> responseBody = new HashMap<>();
    //    responseBody.put("id", "1");
    //    responseBody.put("modifiedBy", "Pranav");
    //    responseWrapper.getWriter().write(gson.toJson(responseBody, Map.class));

    responseWrapper.copyBodyToResponse();
  }
}
