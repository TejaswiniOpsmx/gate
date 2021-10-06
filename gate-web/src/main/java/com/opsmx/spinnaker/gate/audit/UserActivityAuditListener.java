/*
 * Copyright 2021 OpsMx, Inc.
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

package com.opsmx.spinnaker.gate.audit;

import com.opsmx.spinnaker.gate.enums.AuditEventType;
import com.opsmx.spinnaker.gate.enums.OesServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Slf4j
@Component
public class UserActivityAuditListener implements ApplicationListener {

  @Autowired private AuditHandler auditHandler;

  @Override
  public void onApplicationEvent(ApplicationEvent event) {

    log.info("event received ");
    if (event instanceof ServletRequestHandledEvent) {
      ServletRequestHandledEvent servletRequestHandledEvent = (ServletRequestHandledEvent) event;
      if (isAuthenticatedRequest(servletRequestHandledEvent)) {
        log.info("request is authenticated");
        String baseUrl = getBaseUrl(servletRequestHandledEvent.getRequestUrl());
        log.info("base url : {}", baseUrl);
        if (isOesActivity(baseUrl)) {
          log.info("publishing the event to audit service");
          auditHandler.publishEvent(AuditEventType.USER_ACTIVITY_AUDIT, event);
        }
      }
    }
  }

  private boolean isOesActivity(String baseUrl) {

    boolean flag = Boolean.FALSE;
    try {
      switch (OesServices.valueOf(baseUrl)) {
        case oes:
        case autopilot:
        case platformservice:
        case dashboardservice:
        case visibilityservice:
        case auditclientservice:
          flag = Boolean.TRUE;
          break;
      }
    } catch (Exception e) {
      log.info("Not oes event : {}", e);
    }
    return flag;
  }

  private String getBaseUrl(String url) {

    String baseUrl = "";
    if (url != null) {
      baseUrl = url.split("/")[1];
    }
    return baseUrl;
  }

  private boolean isAuthenticatedRequest(ServletRequestHandledEvent servletRequestHandledEvent) {

    boolean flag = Boolean.FALSE;
    if (servletRequestHandledEvent.getUserName() != null
        && !servletRequestHandledEvent.getUserName().trim().isEmpty()
        && servletRequestHandledEvent.getSessionId() != null
        && !servletRequestHandledEvent.getSessionId().trim().isEmpty()) {
      flag = Boolean.TRUE;
    }
    return flag;
  }
}
