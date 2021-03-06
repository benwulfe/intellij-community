/*
 * Copyright 2000-2014 JetBrains s.r.o.
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
package com.intellij.codeInsight.template.postfix.templates;

import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public abstract class ElseExpressionPostfixTemplateBase extends SurroundPostfixTemplateBase
{
  protected ElseExpressionPostfixTemplateBase(@NotNull PostfixTemplatePsiInfo psiInfo,
                                              @NotNull Condition<PsiElement> typeChecker) {
    super("else", "if (!expr)", psiInfo, typeChecker);
  }

  @SuppressWarnings("unchecked")
  protected ElseExpressionPostfixTemplateBase(@NotNull PostfixTemplatePsiInfo psiInfo) {
    super("else", "if (!expr)", psiInfo, Condition.TRUE);
  }


  @Override
  protected PsiElement getWrappedExpression(PsiElement expression) {
    return myPsiInfo.getNegatedExpression(expression);
  }
}
