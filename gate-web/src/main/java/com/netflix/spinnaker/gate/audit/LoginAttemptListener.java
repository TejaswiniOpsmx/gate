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

package com.netflix.spinnaker.gate.audit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
public class LoginAttemptListener {

  //  @EventListener
  //  public void listen(AuditApplicationEvent auditApplicationEvent) {
  //    try {
  //      AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();
  //
  //      log.info("Principal " + auditEvent.getPrincipal() + " - " + auditEvent.getType());
  //
  //      WebAuthenticationDetails details =
  //          (WebAuthenticationDetails) auditEvent.getData().get("details");
  //
  //      log.info("  Remote IP address: " + details.getRemoteAddress());
  //      log.info("  Session Id: " + details.getSessionId());
  //      log.info("  Request URL: " + auditEvent.getData().get("requestUrl"));
  //    } catch (Exception e) {
  //      log.error("Exception occured : {}", e);
  //    }
  //  }
}
