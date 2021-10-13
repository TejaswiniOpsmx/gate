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

package com.opsmx.spinnaker.gate.controllers

import com.google.gson.JsonObject
import com.netflix.spinnaker.gate.services.internal.OpsmxOesService
import groovy.util.logging.Slf4j
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import retrofit.RetrofitError
import retrofit.client.Response

@Slf4j
@RestController
@ConditionalOnExpression('${services.opsmx.enabled:false}')
class OpsmxSaporPolicyController {

  @Autowired
  OpsmxOesService opsmxOesService

  @ApiOperation(value = "Endpoint for sapor runtime policy evaluation rest services")
  @PostMapping(value = "{version}/data/**", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody Object evaluateRuntimePolicy(@PathVariable("version") String version,
                                             @RequestBody(required = false) Object data) {

    return opsmxOesService.evaluateRuntimePolicy(version, data)
  }

  @ApiOperation(value = "Endpoint for sapor static policy evaluation rest services")
  @PostMapping(value = "{version}/staticPolicy/eval", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody Object evaluateStaticPolicy(@PathVariable("version") String version,
                     @RequestBody(required = false) Object data) {

    Response response = opsmxOesService.evaluateStaticPolicy(version, data)
    return new ResponseEntity(response.getBody().asType(Map.class), HttpStatus.valueOf(response.getStatus()))
  }




}
