import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version ("3.0.2")
    id("io.spring.dependency-management") version ("1.1.3")
    kotlin("jvm") version ("1.9.0")
    kotlin("plugin.spring") version ("1.9.0")
}

val springCloudVersion = "2022.0.0"
val springCloudAlibabaVersion = "2022.0.0.0"
val protobufJavaVersion = "2.5.0"
val aliyunLogLogbackAppenderVersion = "0.1.18"
val nacosVersion = "2022.0.0.0"
val easyexcelVersion = "2.1.7"
val aliyunJavaSdkPushVersion = "3.13.9"
val aliyunJavaSdkCoreVersion = "4.5.18"
val aliyunSdkOssVersion = "3.11.2"
val idempotentSpringBootStarterVersion = "0.1.0.RELEASE"
val expiringMapVersion = "0.5.8"
val sentinelVersion = "1.9.4"
val guavaVersion = "30.1.1-jre"
val caffeineVersion = "3.0.0"
val mqVersion = "1.8.8.Final"

group = "com.xujhin"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

// 子项目通用配置
subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    group = "com.xujhin"
    version = "0.0.1-SNAPSHOT"

    // 指定JDK版本
    tasks.jar { enabled = true }
    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        // 缓存层
        implementation("org.springframework.boot:spring-boot-starter-cache")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        // AOP
        implementation("org.springframework.boot:spring-boot-starter-aop")
        // NACOS发现
        implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery:$nacosVersion")
        // NACOS配置
        implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config:$nacosVersion")
        // sentinel
        implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-sentinel")
        // 谷歌工具包
        implementation("com.google.guava:guava:$guavaVersion")
        // 缓存
        implementation("com.github.ben-manes.caffeine:caffeine:$caffeineVersion")
    }

    dependencyManagement {
        dependencies {
//            // MQ
//            implementation("com.aliyun.openservices:ons-client:$mqVersion")
//            // SpringCloud
//            implementation(platform("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion"))
//            // 上传工具
//            implementation("com.aliyun.oss:aliyun-sdk-oss:$aliyunSdkOssVersion")
//            // 熔断器
//            implementation("com.alibaba.csp:spring-boot-starter-ahas-sentinel-client:$sentinelVersion")
        }
        imports {
            mavenBom("${org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES}")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
            mavenBom("com.alibaba.cloud:spring-cloud-alibaba-dependencies:${springCloudAlibabaVersion}")
        }
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

//tasks.named("test") {
//    useJUnitPlatform()
//}
