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

package com.opsmx.spinnaker.gate.rbac;

import com.netflix.spinnaker.gate.services.OesAuthorizationService;
import com.opsmx.spinnaker.gate.enums.FeatureType;
import com.opsmx.spinnaker.gate.enums.PermissionEnum;
import com.opsmx.spinnaker.gate.exception.AccessForbiddenException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationFeatureRbac {

  @Autowired private OesAuthorizationService oesAuthorizationService;

  private static final List<String> runtime_access = new ArrayList<>();
  public static final List<String> applicationFeatureRbacEndpoints = new ArrayList<>();

  static {
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/users/{username}/applications/latest-canary");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/autopilot/service/feature/configuration");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v1/dashboard/{username}/applications");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/applications/{applicationId}/pending_approvals");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/applications/{applicationId}/services/{serviceId}/deploymentHistory/{deploymentHistoryId}/gates");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v3/pipelines/{pipelineId}/gates/{gateId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/pipelines/{pipelineId}/gates/{gateId}");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v1/application/{applicationId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v3/applications/{applicationId}/service/{serviceId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/applications/{applicationId}/deploymentHistory/{deploymentHistoryId}/gates");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/application/{applicationId}");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v3/applications/{applicationId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/autopilot/service/{serviceId}/application/{applicationId}/feature/configuration");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/pipelines/{pipelineId}/gates/{gateId}/references/{refId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/applications/{applicationId}/usergroups/permissions");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/pipelines/{pipelineId}/gates/{gateId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/applications/{applicationId}/service");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v3/pipelines/{pipelineId}/gates/{gateId}/references/{refId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/applications/{applicationId}/services/{serviceId}/deployments");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v1/applications/{applicationId}");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/{userId}/applications");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/applications/{applicationId}/environments/{environmentName}");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v3/pipelines/{pipelineId}/gates");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/applications/{applicationId}/deployments/history");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v4/pipelines/{pipelineId}/gates");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/applications/{applicationId}/services/{serviceId}/deployments/history");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/applications/{applicationId}/service/{serviceId}");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/users/{username}/applications");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/applications/{applicationId}/service/{serviceId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v3/pipelines/{pipelineId}/gates/{gateId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/applications/{applicationId}/deployments");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v1/users/{username}/applications/latest-canary");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/applications/{applicationId}/services");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/applications/{applicationId}/services/{serviceId}/deploymentCurrents/{deploymentCurrentId}/gates");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/visibilityservice/service/{serviceId}/feature/configuration/{approvalGateId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v1/applications/service/{applicationId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v3/applications/{applicationId}/services");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/applications/{applicationId}/usergroups/permissions");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/applications/{applicationId}/services");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/dashboard/{username}/applications");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/visibilityservice/service/feature/configuration");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/applications/{applicationId}");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v4/applications/{applicationId}/services/{serviceId}/deployments/current");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/application");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v1/application");
    applicationFeatureRbacEndpoints.add(
        "/dashboardservice/v2/sapor/service/{serviceId}/application/{applicationId}/feature/configuration");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v4/users/{username}/applications");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/sapor/service/feature/configuration");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/services/{serviceId}/gates/{id}");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/services/{serviceId}/gates/{id}");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/applications/{applicationId}");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v4/users/{username}/summary");
    applicationFeatureRbacEndpoints.add("/dashboardservice/v2/services/{serviceId}/gates");

    // applicationFeatureRbacEndpoints.addAll(runtime_access);
  }

  public void authorizeUser(String userName, String endpointUrl, String httpMethod) {
    HttpMethod method = HttpMethod.valueOf(httpMethod);
    Boolean isAuthorized;

    switch (method) {
      case GET:
        isAuthorized =
            oesAuthorizationService
                .authorizeUser(
                    userName, FeatureType.APP.name(), PermissionEnum.view.name(), userName)
                .getBody();
        log.info("is Authorized : {}", isAuthorized);
        if (!isAuthorized) {
          throw new AccessForbiddenException(
              "You do not have "
                  + PermissionEnum.view.name()
                  + " permission for the feature type : "
                  + FeatureType.APP.name()
                  + " to perform this operation");
        }
        break;

      case POST:
      case PUT:
        isAuthorized =
            oesAuthorizationService
                .authorizeUser(
                    userName,
                    FeatureType.APP.name(),
                    PermissionEnum.create_or_edit.name(),
                    userName)
                .getBody();
        log.info("is Authorized : {}", isAuthorized);
        if (!isAuthorized) {
          throw new AccessForbiddenException(
              "You do not have "
                  + PermissionEnum.create_or_edit.name()
                  + " permission for the feature type : "
                  + FeatureType.APP.name()
                  + " to perform this operation");
        }
        break;

      case DELETE:
        isAuthorized =
            oesAuthorizationService
                .authorizeUser(
                    userName, FeatureType.APP.name(), PermissionEnum.delete.name(), userName)
                .getBody();
        log.info("is Authorized : {}", isAuthorized);
        if (!isAuthorized) {
          throw new AccessForbiddenException(
              "You do not have "
                  + PermissionEnum.delete.name()
                  + " permission for the feature type : "
                  + FeatureType.APP.name()
                  + " to perform this operation");
        }
        break;
    }
  }
}
