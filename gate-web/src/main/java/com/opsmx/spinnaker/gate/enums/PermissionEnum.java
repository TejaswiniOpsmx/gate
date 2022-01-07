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

package com.opsmx.spinnaker.gate.enums;

public enum PermissionEnum {
  view("view a feature"),
  create_or_edit("create or edit a feature"),
  delete("delete a feature"),
  runtime_access("execute"),
  global_view("view all in audit trail");

  public String description;

  public String getDescription() {
    return this.description;
  }

  PermissionEnum(String description) {
    this.description = description;
  }
}
