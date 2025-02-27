import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.micronaut.gradle.MicronautExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    // this list needs to be kept in sync with the plugins list in settings.gradle.kts
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.allopen")
    kotlin("plugin.noarg")
    id("io.micronaut.application")
    id("com.github.ben-manes.versions")
    application
}

val avroVersion: String by project
val commonsIoVersion: String by project
val commonsLangVersion: String by project
val commonsValidatorVersion: String by project
val confluentVersion: String by project
val coroutinesVersion: String by project
val javaxActivationVersion: String by project
val kotestVersion: String by project
val kotestWiremockExtensionVersion: String by project
val kotlinLoggingVersion: String by project
val kotlinVersion: String by project
val log4jVersion: String by project
val micronautAvroVersion: String by project
val micronautCommonVersion: String by project
val micronautKotestVersion: String by project
val micronautKotest5Version: String by project
val micronautMongoVersion: String by project
val micronautReactorVersion: String by project
val micronautTracingVersion: String by project
val micronautValidationVersion: String by project
val micronautVersion: String by project
val mockkVersion: String by project
val openHtmlToPdfVersion: String by project
val ossValidatorVersion: String by project
val pdfBoxVersion: String by project
val poiVersion: String by project
val postgresVersion: String by project
val resilience4jVersion: String by project
val shedlockJdbcProvider: String by project
val shedlockMongoProvider: String by project
val shedlockProvider: String by project
val thymeleafVersion: String by project
val uuidVersion: String by project
val wiremockVersion: String by project
val flywayPostgresVersion: String by project
val flywayVersion: String by project
val awsJavaSdkS3Version: String by project
val postgreSQLVersion: String by project
allprojects {
    version = "0.0.1"
    group = "foo"

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.ben-manes.versions")

    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    tasks {
        val javaVersion: String by extra { "17" }
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
                jvmTarget = javaVersion
                javaParameters = true
            }
        }

        withType<JavaExec> {
            jvmArgs("-XX:TieredStopAtLevel=1")
            systemProperties = this.systemProperties + System.getProperties() as Map<String, Any>
            environment = this.environment + System.getenv() as Map<String, Any>
        }

        withType<DependencyUpdatesTask> {
            rejectVersionIf {
                (
                        "[0-9,.v-]+(-r)?".toRegex().matches(candidate.version) ||
                                listOf("RELEASE", "FINAL", "GA").contains(candidate.version.uppercase())
                        ).not()
            }
        }
    }
}

extensions.findByName("buildScan")?.withGroovyBuilder {
    setProperty("termsOfServiceUrl", "https://gradle.com/terms-of-service")
    setProperty("termsOfServiceAgree", "yes")
}

configurations.all {
    resolutionStrategy {
        // for SCA issue
        exclude("com.fasterxml.woodstox:woodstox-core:6.2.4")
        force("com.fasterxml.woodstox:woodstox-core:6.4.0")
    }
}

subprojects {
    val subproject = this

    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "org.jetbrains.kotlin.plugin.noarg")
    apply(plugin = "io.micronaut.application")

    dependencies {
        kapt("io.micronaut:micronaut-http-validation")
        kapt("io.micronaut.openapi:micronaut-openapi")
        compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")
        implementation("io.micronaut:micronaut-http-client")
        implementation("io.micronaut:micronaut-management")
        implementation("io.micronaut:micronaut-runtime")
        implementation("io.micronaut.data:micronaut-data-model")
        implementation("io.micronaut.data:micronaut-data-runtime")
        implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
        implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
        implementation("io.micronaut:micronaut-tracing:${micronautTracingVersion}")
        implementation("javax.annotation:javax.annotation-api")
        implementation("io.swagger.core.v3:swagger-annotations")
        implementation("io.micronaut:micronaut-validation:${micronautValidationVersion}")
        implementation("com.fasterxml.jackson.module:jackson-module-afterburner")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
        implementation("io.github.microutils:kotlin-logging:${kotlinLoggingVersion}")
        implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
        implementation("org.bouncycastle:bcprov-jdk18on:1.78")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${coroutinesVersion}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${coroutinesVersion}")
        implementation("commons-validator:commons-validator:${commonsValidatorVersion}")
        implementation("org.apache.commons:commons-lang3:${commonsLangVersion}")
        implementation("com.fasterxml.uuid:java-uuid-generator:${uuidVersion}")
        implementation("commons-io:commons-io:${commonsIoVersion}")
        implementation("org.thymeleaf:thymeleaf:${thymeleafVersion}")
        implementation("com.openhtmltopdf:openhtmltopdf-pdfbox:${openHtmlToPdfVersion}")

        runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
        runtimeOnly("org.apache.logging.log4j:log4j-core:${log4jVersion}")
        runtimeOnly("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jVersion")
        runtimeOnly("org.yaml:snakeyaml")

        // Avro
        implementation("org.apache.avro:avro:${avroVersion}")

        implementation("org.postgresql:postgresql:$postgreSQLVersion")
        implementation("io.micronaut.micrometer:micronaut-micrometer-core")
        implementation("io.micronaut.micrometer:micronaut-micrometer-registry-influx")
        implementation("com.amazonaws:aws-java-sdk-s3:$awsJavaSdkS3Version")

        testImplementation("io.micronaut.reactor:micronaut-reactor-http-client:${micronautReactorVersion}")
        testImplementation("io.micronaut.test:micronaut-test-kotest:${micronautKotestVersion}")
        testImplementation("org.wiremock:wiremock:${wiremockVersion}")
        testImplementation("io.kotest.extensions:kotest-extensions-wiremock:${kotestWiremockExtensionVersion}")

        testRuntimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
        testRuntimeOnly("org.apache.logging.log4j:log4j-core:${log4jVersion}")
        testRuntimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:${log4jVersion}")

        kaptTest("io.micronaut:micronaut-inject-java")
    }

    kapt {
        correctErrorTypes = true
    }

    configurations.all {
        exclude(group = "ch.qos.logback")
        exclude(module = "log4j-jul")
        exclude(module = "log4j-slf4j-impl")
    }

    the<MicronautExtension>().apply {
        version.set(micronautVersion)
        runtime("netty")
        testRuntime("kotest")
        processing {
            incremental(true)
            annotations("foo.*")
        }
    }


    application {
        mainClass.set("foo.apps.${subproject.name}.Application")
    }
}

tasks {
    val generateVela by registering(Exec::class) {
        workingDir("devops")
        commandLine("./generate-vela.kts")
    }
}
