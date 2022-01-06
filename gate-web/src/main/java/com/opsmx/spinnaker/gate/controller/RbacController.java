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

package com.opsmx.spinnaker.gate.controller;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/oes")
public class RbacController {

  private Gson gson = new Gson();

  @PostMapping(value = "/rbac", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> validate(
      @RequestBody String requestBody, HttpServletRequest request) {

    log.info("uri : {}", request.getRequestURI());
    log.info("actualUrl : {}", request.getHeader("actualUrl"));
    log.info("request body : {}", requestBody);
    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/rbac", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> validateGet(HttpServletRequest request) {

    log.info("uri : {}", request.getRequestURI());
    log.info("actualUrl : {}", request.getHeader("actualUrl"));
    return ResponseEntity.ok().build();
  }
}
