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
package pub.ihub.plugin.bom

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Action
import org.gradle.api.plugins.JavaPlatformPlugin
import org.gradle.api.plugins.catalog.VersionCatalogPlugin
import pub.ihub.plugin.IHubPlugin
import pub.ihub.plugin.IHubProjectPluginAware

import static pub.ihub.plugin.IHubLibsVersions.IHUB_LIBS_LOCAL_VERSION
import static pub.ihub.plugin.IHubLibsVersions.IHubLibsVersion
import static pub.ihub.plugin.IHubProjectPluginAware.EvaluateStage.AFTER

/**
 * BOM（Bill of Materials）组件依赖管理
 * @author henry
 */
@IHubPlugin(IHubBomExtension)
@SuppressWarnings('NestedBlockDepth')
class IHubBomPlugin extends IHubProjectPluginAware<IHubBomExtension> {

    @Override
    void apply() {
        // 如果项目为Java平台组件项目时，不执行插件
        if (hasPlugin(JavaPlatformPlugin)) {
            logger.trace 'Java platform project, skip apply ihub-bom plugin'
            return
        }

        project.gradle.taskGraph.whenReady {
            extension.printConfigContent()
        }

        // 如果项目为版本目录组件项目时，不继续执行插件
        if (hasPlugin(VersionCatalogPlugin)) {
            logger.trace 'Version catalog project, skip apply ihub-bom plugin'
            return
        }

        // 项目不包含dependencies组件时，自动配置ihub-bom
        if (!project.hasProperty('iHubSettings.includeDependencies')) {
            def iHubLibsVersion = project.findProperty(IHUB_LIBS_LOCAL_VERSION) ?: IHubLibsVersion
            extension.importBoms {
                group 'pub.ihub.lib' module 'ihub-dependencies' version iHubLibsVersion
            }
        }

        // 配置项目依赖
        withExtension(AFTER) { ext ->
            configVersions ext

            configProject ext

            ext.refreshCommonSpecs()
        }
    }

    private void configVersions(IHubBomExtension ext) {
        // 导入bom配置
        if (ext.bomVersions) {
            dependencyManagement {
                it.imports {
                    ext.bomVersions.each {
                        mavenBom "$it.id:$it.module:$it.version"
                    }
                }
            }
        }

        // 配置组件版本
        if (ext.dependencyVersions) {
            dependencyManagement {
                it.dependencies {
                    ext.dependencyVersions.each { config ->
                        dependencySet(group: config.id, version: config.version) {
                            config.modules.each { entry it }
                        }
                    }
                }
            }
        }
    }

    private void configProject(IHubBomExtension ext) {
        project.configurations {
            all {
                resolutionStrategy {
                    // 配置组件组版本（用于配置无bom组件）
                    eachDependency {
                        ext.groupVersions.find { s -> s.id == it.requested.group }?.version?.with { v ->
                            it.useVersion v
                        }
                    }
                    // 不缓存动态版本
                    cacheDynamicVersionsFor 0, 'seconds'
                    // 不缓存快照模块
                    cacheChangingModulesFor 0, 'seconds'
                }
                // 排除组件依赖
                ext.excludeModules.each {
                    if (it.modules.contains('all')) {
                        exclude group: it.group
                    } else {
                        it.modules.each { module -> exclude group: it.group, module: module }
                    }
                }
            }
            // 配置组件需要能力
            ext.capabilities.each { spec ->
                compileClasspath {
                    incoming.beforeResolve {
                        dependencies.find { spec.dependency ==~ /$it.group|$it.group:$it.name|$it.name/ }?.with { dep ->
                            project.dependencies {
                                spec.capabilities.each { module ->
                                    implementation("$dep.group:$dep.name") {
                                        capabilities {
                                            requireCapability module.contains(':') ? module : "${dep.group}:$module"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void dependencyManagement(Action<DependencyManagementExtension> action) {
        applyPlugin DependencyManagementPlugin
        withExtension DependencyManagementExtension, action
    }

}
