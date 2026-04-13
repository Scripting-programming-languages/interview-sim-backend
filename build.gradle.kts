plugins {
	java
	id("org.springframework.boot") version "4.0.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.openapi.generator") version "7.19.0"
	id("com.google.protobuf") version "0.9.4"
}



group = "com.github.scripting.programming.language"
version = "0.0.1-SNAPSHOT"
description = "Backend for interview simulator project"
val springGrpcVersion = "1.1.0-SNAPSHOT"
val protobufVersion = "3.25.5"
val grpcVersion = "1.69.0"
val mapstructVersion = "1.6.3"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.grpc:spring-grpc-dependencies:$springGrpcVersion")
	}
}


configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-batch")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-kafka")
	implementation("org.springframework.boot:spring-boot-starter-liquibase")
	implementation("org.springframework.boot:spring-boot-starter-quartz")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.springframework.grpc:spring-grpc-dependencies:1.1.0-M1")
	implementation("org.springframework.grpc:spring-grpc-spring-boot-starter:$springGrpcVersion")
	implementation("io.grpc:grpc-protobuf:$grpcVersion")
	implementation("io.grpc:grpc-stub:$grpcVersion")

	implementation("org.apache.httpcomponents.client5:httpclient5")
	implementation("io.jsonwebtoken:jjwt-api:0.13.0")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")
	implementation("jakarta.validation:jakarta.validation-api")
	implementation("io.swagger.core.v3:swagger-annotations-jakarta:2.2.28")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.mapstruct:mapstruct:$mapstructVersion")
	annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	compileOnly("org.projectlombok:lombok")
	compileOnly("jakarta.annotation:jakarta.annotation-api:3.0.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
	testImplementation("org.springframework.boot:spring-boot-starter-batch-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jdbc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-redis-test")
	testImplementation("org.springframework.boot:spring-boot-starter-kafka-test")
	testImplementation("org.springframework.boot:spring-boot-starter-liquibase-test")
	testImplementation("org.springframework.boot:spring-boot-starter-quartz-test")
	testImplementation("org.springframework.boot:spring-boot-starter-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:testcontainers-junit-jupiter")
	testImplementation("org.testcontainers:testcontainers-kafka")
	testImplementation("org.testcontainers:testcontainers-postgresql")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.compileJava {
	dependsOn(tasks.openApiGenerate)
	dependsOn("generateProto")
}

sourceSets {
	main {
		java {
			srcDirs(
				"src/main/java",
				"build/generated/openapi/src/main/java",
				"build/generated/source/proto/main/grpc",
				"build/generated/source/proto/main/java"
			)
		}
	}
}

openApiGenerate {
	generatorName = "spring"
	inputSpec.set("$projectDir/src/main/resources/static/openapi/front-api.yaml")
	outputDir.set("$projectDir/build/generated/openapi")
	apiPackage.set("com.github.scripting.programming.language.controller")
	modelPackage.set("com.github.scripting.programming.language.model")
	globalProperties = mapOf("apis" to "", "models" to "")
	configOptions = mapOf(
		"skipDefaultInterface" to "true",
		"interfaceOnly" to "true",
		"serializableModel" to "true",
		"hideGenerationTimestamp" to "true",
		"useTags" to "true",
		"useBeanValidation" to "true",
		"generateSupportingFiles" to "false",
		"library" to "spring-boot",
		"openApiNullable" to "false",
		"useJakartaEe" to "true"
	)
}


protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:$protobufVersion"
	}
	plugins {
		create("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
		}
	}
	generateProtoTasks {
		all().forEach { task ->
			task.plugins {
				create("grpc") {
					option("@generated=omit")
				}
			}
		}
	}
}