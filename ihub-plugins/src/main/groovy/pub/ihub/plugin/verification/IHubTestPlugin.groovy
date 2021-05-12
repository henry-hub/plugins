/*
 * Copyright (c) 2021 Henry 李恒 (henry.box@outlook.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pub.ihub.plugin.verification

import static pub.ihub.plugin.IHubPluginAware.EvaluateStage.AFTER
import static pub.ihub.plugin.IHubPluginAware.EvaluateStage.BEFORE

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import pub.ihub.plugin.IHubPluginAware
import pub.ihub.plugin.bom.IHubBomExtension

/**
 * 测试插件
 * @author henry
 */
class IHubTestPlugin implements IHubPluginAware<IHubTestExtension> {

	@Override
	void apply(Project project) {
		getExtension(project, IHubBomExtension, BEFORE) {
			it.dependencies {
				testImplementation 'org.spockframework:spock-spring'
				testRuntimeOnly 'com.athaydes:spock-reports'
			}
		}
		createExtension(project, 'iHubTest', IHubTestExtension, AFTER) { ext ->
			project.tasks.getByName('test') { Test it ->
				it.useJUnitPlatform()
				if (ext.testClasses) {
					ext.testClasses.tokenize(',').each { testClass ->
						it.include testClass
					}
				} else {
					it.include '**/*Test*', '**/*FT*', '**/*UT*'
				}

				it.forkEvery = ext.testForkEvery
				it.maxParallelForks = ext.testMaxParallelForks

				it.systemProperties = System.properties as Map

				it.onOutput { descriptor, event ->
					it.logger.lifecycle event.message
				}
			}
		}
	}

}
