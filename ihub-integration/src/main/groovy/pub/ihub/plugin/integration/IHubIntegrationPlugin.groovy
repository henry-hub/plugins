/*
 * Copyright (c) 2024 the original author or authors.
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
package pub.ihub.plugin.integration

import net.bytebuddy.build.gradle.ByteBuddyPlugin
import org.gradle.api.plugins.GroovyPlugin
import pub.ihub.plugin.IHubPlugin
import pub.ihub.plugin.IHubProjectPluginAware
import pub.ihub.plugin.java.IHubJavaPlugin

import java.nio.file.Path

import static java.nio.file.Files.*

/**
 * Java集成插件
 * @author henry
 * @since 1.5.4
 */
@IHubPlugin(value = IHubIntegrationExtension, beforeApplyPlugins = [IHubJavaPlugin, ByteBuddyPlugin])
class IHubIntegrationPlugin extends IHubProjectPluginAware<IHubIntegrationExtension> {

    @Override
    protected void apply() {
        writeModulesSourcesPaths()
        writeEnableDebug()
    }

    private File getIntegrationDir() {
        project.rootProject.layout.projectDirectory.dir('.gradle').dir('pub.ihub.plugin.integration').asFile
    }

    private void writeModulesSourcesPaths() {
        Path pathsPath = integrationDir.toPath().resolve 'modules-sources-paths'
        createDirectories pathsPath.parent
        List<String> lines = exists(pathsPath) ? readAllLines(pathsPath) : []
        lines << project.file('src/main/java').path
        if (hasPlugin(GroovyPlugin)) {
            lines << project.file('src/main/groovy').path
        }
        write pathsPath, lines.toSet()
    }

    private void writeEnableDebug() {
        Path pathsPath = integrationDir.toPath().resolve 'enable-debug'
        createDirectories pathsPath.parent
        pathsPath.toFile().delete()
        writeString pathsPath, extension.enableDebug.get().toString()
    }

}
