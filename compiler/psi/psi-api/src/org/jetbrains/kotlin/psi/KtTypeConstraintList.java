/*
 * Copyright 2010-2015 JetBrains s.r.o.
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

package org.jetbrains.kotlin.psi;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.KtStubBasedElementTypes;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;

import java.util.List;

public class KtTypeConstraintList extends KtElementImplStub<KotlinPlaceHolderStub<KtTypeConstraintList>> {
    public KtTypeConstraintList(@NotNull ASTNode node) {
        super(node);
    }

    public KtTypeConstraintList(@NotNull KotlinPlaceHolderStub<KtTypeConstraintList> stub) {
        super(stub, KtStubBasedElementTypes.TYPE_CONSTRAINT_LIST);
    }

    @Override
    public <R, D> R accept(@NotNull KtVisitor<R, D> visitor, D data) {
        return visitor.visitTypeConstraintList(this, data);
    }

    @NotNull
    public List<KtTypeConstraint> getConstraints() {
        return getStubOrPsiChildrenAsList(KtStubBasedElementTypes.TYPE_CONSTRAINT);
    }
}
