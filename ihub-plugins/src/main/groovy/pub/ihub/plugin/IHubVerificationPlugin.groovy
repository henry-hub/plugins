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

package pub.ihub.plugin


import org.gradle.api.Project
import org.gradle.api.plugins.GroovyPlugin
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.quality.CodeNarcExtension
import org.gradle.api.plugins.quality.CodeNarcPlugin
import org.gradle.api.plugins.quality.PmdExtension
import org.gradle.api.plugins.quality.PmdPlugin
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension



/**
 * @author liheng
 */
class IHubVerificationPlugin implements IHubPluginAware<Project> {

	@Override
	void apply(Project project) {
		if (project.plugins.hasPlugin(JavaPlugin) || project.plugins.hasPlugin(JavaLibraryPlugin)) {
			configPmd project
		}
		if (project.plugins.hasPlugin(GroovyPlugin)) {
			configCodenarc project
		}
		configJacoco project
	}

	private void configPmd(def target) {
		target.pluginManager.apply PmdPlugin
		target.extensions.getByType(PmdExtension).identity {
			toolVersion = findProperty target, 'pmd.version', '6.30.0'
			ruleSetFiles()
			ruleSets = [
				'rulesets/java/ali-comment.xml',
				'rulesets/java/ali-concurrent.xml',
				'rulesets/java/ali-constant.xml',
				'rulesets/java/ali-exception.xml',
				'rulesets/java/ali-flowcontrol.xml',
				'rulesets/java/ali-naming.xml',
				'rulesets/java/ali-oop.xml',
				'rulesets/java/ali-orm.xml',
				'rulesets/java/ali-other.xml',
				'rulesets/java/ali-set.xml',
				'rulesets/vm/ali-other.xml',
//                    'rulesets/java/basic.xml',
//                    'rulesets/java/braces.xml',
//                    'rulesets/java/clone.xml',
//                    'rulesets/java/codesize.xml',
//                    'rulesets/java/comments.xml',
//                    'rulesets/java/controversial.xml',
//                    'rulesets/java/coupling.xml',
//                    'rulesets/java/design.xml',
//                    'rulesets/java/empty.xml',
//                    'rulesets/java/finalizers.xml',
				'rulesets/java/imports.xml',
//                    'rulesets/java/javabeans.xml',
//                    'rulesets/java/logging-jakarta-commons.xml',
//                    'rulesets/java/logging-java.xml',
//                    'rulesets/java/metrics.xml',
//                    'rulesets/java/naming.xml',
//                    'rulesets/java/optimizations.xml',
//                    'rulesets/java/quickstart.xml',
//                    'rulesets/java/strictexception.xml',
//                    'rulesets/java/strings.xml',
//                    'rulesets/java/sunsecure.xml',
//                    'rulesets/java/typeresolution.xml',
//                    'rulesets/java/unnecessary.xml',
//                    'rulesets/java/unusedcode.xml',
			]
		}
	}

	private void configCodenarc(def target) {
		target.pluginManager.apply CodeNarcPlugin
		target.extensions.getByType(CodeNarcExtension).identity {
			toolVersion = findProperty target, 'codenarc.version', '1.6.1'
			// TODO 处理配置文件
			configFile = target.rootProject.file 'conf/engineering-process/static-checking/groovy/codenarc.gcfg'
			ignoreFailures = false
		}
	}

	private void configJacoco(def target) {
		target.pluginManager.apply JacocoPlugin
		target.extensions.getByType(JacocoPluginExtension).identity {
			toolVersion = findProperty target, 'jacoco.version', '0.8.6'
		}
		// TODO 配置检查规则
	}

}
