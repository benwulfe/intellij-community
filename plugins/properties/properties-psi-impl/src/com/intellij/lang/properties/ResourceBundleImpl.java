/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Alexey
 */
package com.intellij.lang.properties;

import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.SmartList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResourceBundleImpl extends ResourceBundle {
  @NotNull private final PropertiesFile myDefaultPropertiesFile;

  public ResourceBundleImpl(@NotNull final PropertiesFile defaultPropertiesFile) {
    myDefaultPropertiesFile = defaultPropertiesFile;
  }

  @NotNull
  @Override
  public List<PropertiesFile> getPropertiesFiles() {
    PsiFile[] children = myDefaultPropertiesFile.getParent().getFiles();
    final String baseName = getBaseName();
    List<PropertiesFile> result = new SmartList<PropertiesFile>();
    for (PsiFile file : children) {
      if (!file.isValid() || file.getVirtualFile().getExtension() == null) continue;
      if (Comparing.strEqual(PropertiesUtil.getBaseName(file), baseName)) {
        PropertiesFile propertiesFile = PropertiesImplUtil.getPropertiesFile(file);
        if (propertiesFile != null) {
          result.add(propertiesFile);
        }
      }
    }
    return result;
  }

  @NotNull
  @Override
  public List<PropertiesFile> getPropertiesFiles(final Project project) {
    return getPropertiesFiles();
  }

  @NotNull
  @Override
  public PropertiesFile getDefaultPropertiesFile() {
    return myDefaultPropertiesFile;
  }

  @NotNull
  @Override
  public PropertiesFile getDefaultPropertiesFile(final Project project) {
    return getDefaultPropertiesFile();
  }

  @NotNull
  @Override
  public String getBaseName() {
    return PropertiesUtil.getBaseName(myDefaultPropertiesFile.getContainingFile());
  }

  @NotNull
  public VirtualFile getBaseDirectory() {
    return myDefaultPropertiesFile.getParent().getVirtualFile();
  }

  @NotNull
  @Override
  public Project getProject() {
    return myDefaultPropertiesFile.getProject();
  }

  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final ResourceBundleImpl resourceBundle = (ResourceBundleImpl)o;
    if (!myDefaultPropertiesFile.equals(resourceBundle.myDefaultPropertiesFile)) return false;
    return true;
  }

  public int hashCode() {
    return myDefaultPropertiesFile.hashCode();
  }

  public String getUrl() {
    return getBaseDirectory() + "/" + getBaseName();
  }
}