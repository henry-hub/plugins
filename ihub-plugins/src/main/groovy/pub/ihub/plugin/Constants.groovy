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

/**
 * @author liheng
 */
class Constants {

	static final MAVEN_CENTRAL_REPO_CUSTOMIZE = 'mavenCentralRepoCustomize'

	static final GRADLE_COMPILATION_INCREMENTAL = 'gradleCompilationIncremental'
	static final JAVA_COMPATIBILITY = 'javaCompatibility'

	//<editor-fold desc="仓库相关相关">

	static final MAVEN_LOCAL_ENABLED = 'mavenLocalEnabled'

	static final RELEASE_REPOSITORY_URL = 'releaseRepositoryUrl'
	static final SNAPSHOT_REPOSITORY_URL = 'snapshotRepositoryUrl'
	static final SONATYPE_NEXUS_USERNAME = 'sonatypeNexusUsername'
	static final SONATYPE_NEXUS_PASSWORD = 'sonatypeNexusPassword'

	static final DEFAULT_RELEASE_REPOSITORY_URL = 'https://oss.sonatype.org/service/local/staging/deploy/maven2/'
	static final DEFAULT_SNAPSHOT_REPOSITORY_URL = 'https://oss.sonatype.org/content/repositories/snapshots/'

	//</editor-fold>

	//<editor-fold desc="Plugins组件相关">

	static final List<List<String>> GROUP_MAVEN_BOM_VERSION_CONFIG = [
		['org.springframework.boot'		, 'spring-boot-dependencies'			, '2.4.4'				],
		['org.springframework.cloud'	, 'spring-cloud-dependencies'			, '2020.0.2'			],
		['org.springframework.security'	, 'spring-security-bom'					, '5.4.5'				],
		['com.alibaba.cloud'			, 'spring-cloud-alibaba-dependencies'	, '2.2.5.RELEASE'		],
		['com.github.xiaoymin'			, 'knife4j-dependencies'				, '3.0.2'				],
		['com.sun.xml.bind'				, 'jaxb-bom-ext'						, '3.0.0'				],
		['de.codecentric'				, 'batch-web-spring-boot-dependencies'	, '2.1.0.RELEASE'		],
		['de.codecentric'				, 'spring-boot-admin-dependencies'		, '2.4.0'				],
	]

	static final Map<String, String> GROUP_MAVEN_VERSION_CONFIG = [
		'pub.ihub.lib': 'dev-SNAPSHOT',
		'cn.hutool'   : '5.6.2',
	]

	static final List GROUP_DEPENDENCY_VERSION_CONFIG = [
		['org.projectlombok'	, '1.18.16'			, ['lombok']],
		['javax.xml.bind'		, '2.3.1'			, ['jaxb-api']],
		['com.baomidou'			, '3.4.2'			, ['mybatis-plus', 'mybatis-plus-boot-starter',
														   'mybatis-plus-generator']],
		['com.github.xiaoymin'	, '2.0.8'			, ['knife4j-aggregation-spring-boot-starter']],
	]

	static final Map<String, List<String>> GROUP_DEPENDENCY_EXCLUDE_MAPPING = [
		'c3p0'                    : ['c3p0'],
		'commons-logging'         : ['commons-logging'],
		'com.zaxxer'              : ['HikariCP'],
		'log4j'                   : ['log4j'],
		'org.apache.logging.log4j': ['log4j-core'],
		'org.apache.tomcat'       : ['tomcat-jdbc'],
		'org.slf4j'               : ['slf4j-jcl', 'slf4j-log4j12'],
		'stax'                    : ['stax-api']
	]

	static final Map<String, List<String>> GROUP_DEFAULT_DEPENDENCIES_MAPPING = [
		compileOnly          : ['cn.hutool:hutool-all'],
		implementation       : ['org.slf4j:slf4j-api'],
		api                  : [],
		runtimeOnly          : ['org.slf4j:jul-to-slf4j', 'org.slf4j:jcl-over-slf4j', 'org.slf4j:log4j-over-slf4j'],
		testImplementation   : [],
		debugImplementation  : [],
		releaseImplementation: []
	]

	//</editor-fold>

	//<editor-fold desc="Publish组件相关">

	static final RELEASE_SIGNING_ENABLED = 'releaseSigningEnabled'
	static final RELEASE_DOCS_ENABLED = 'releaseDocsEnabled'
	static final RELEASE_SOURCES_ENABLED = 'releaseSourcesEnabled'

	//</editor-fold>

}
