// Copyright (C) 2013 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.server.config;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gerrit.extensions.annotations.ExtensionPoint;

import java.util.Arrays;
import java.util.List;

@ExtensionPoint
public class ProjectConfigEntry {
  public enum Type {
    STRING, INT, LONG, BOOLEAN, LIST
  }

  private final String displayName;
  private final String defaultValue;
  private final Type type;
  private final List<String> permittedValues;

  public ProjectConfigEntry(String displayName, String defaultValue) {
    this(displayName, defaultValue, Type.STRING, null);
  }

  public ProjectConfigEntry(String displayName, int defaultValue) {
    this(displayName, Integer.toString(defaultValue), Type.INT, null);
  }

  public ProjectConfigEntry(String displayName, long defaultValue) {
    this(displayName, Long.toString(defaultValue), Type.LONG, null);
  }

  public ProjectConfigEntry(String displayName, boolean defaultValue) {
    this(displayName, Boolean.toString(defaultValue), Type.BOOLEAN, null);
  }

  public ProjectConfigEntry(String displayName, String defaultValue,
      List<String> permittedValues) {
    this(displayName, defaultValue, Type.LIST, permittedValues);
  }

  public <T extends Enum<?>> ProjectConfigEntry(String displayName,
      T defaultValue, Class<T> permittedValues) {
    this(displayName, defaultValue.name(), Type.LIST, Lists.transform(
        Arrays.asList(permittedValues.getEnumConstants()),
        new Function<Enum<?>, String>() {
          @Override
          public String apply(Enum<?> e) {
            return e.name();
          }
        }));
  }

  private ProjectConfigEntry(String displayName, String defaultValue,
      Type type, List<String> permittedValues) {
    this.displayName = displayName;
    this.defaultValue = defaultValue;
    this.type = type;
    this.permittedValues = permittedValues;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public Type getType() {
    return type;
  }

  public List<String> getPermittedValues() {
    return permittedValues;
  }
}
