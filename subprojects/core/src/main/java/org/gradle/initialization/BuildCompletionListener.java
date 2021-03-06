/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.initialization;

import org.gradle.internal.service.scopes.EventScope;
import org.gradle.internal.service.scopes.Scopes;

/**
 * Note: this listener type is never notified. This type is used by the Android plugin lint tasks.
 */
@Deprecated
@EventScope(Scopes.Build.class)
public interface BuildCompletionListener {
    /**
     * Called after a build has completed and all its services and domain objects torn down. Implementations should take care to not use any such services.
     */
    void completed();
}
