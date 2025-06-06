/*
 * Copyright (c) 2021-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pub.ihub.plugin.groovy

import org.gradle.api.plugins.GroovyPlugin
import pub.ihub.plugin.IHubPlugin
import pub.ihub.plugin.IHubProjectPluginAware
import pub.ihub.plugin.bom.IHubBomExtension
import pub.ihub.plugin.java.IHubJavaBasePlugin

/**
 * Groovy插件
 * @author liheng
 */
@IHubPlugin(beforeApplyPlugins = [IHubJavaBasePlugin, GroovyPlugin])
class IHubGroovyPlugin extends IHubProjectPluginAware {

    @Override
    void apply() {
        withExtension(IHubBomExtension) {
            it.dependencies {
                implementation ihub.bundles.groovy.get() as Object[]
            }
        }
    }

}
