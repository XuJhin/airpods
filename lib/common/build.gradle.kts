import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

fun plugins() {
    // 这里添加你的插件声明，例如 Spring Boot 插件
    // 例如：`id("org.springframework.boot") version "2.5.5"`
}
// 设置项目的归档基本名称
archivesName.set("lib")

// 取消打包为 bootJar 可独立运行包，因为 common 只是一个普通的 JAR 包
// 如果需要独立运行的 JAR 包，请将这个属性设置为 true
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
    archiveBaseName = "lib"
}


dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.apache.commons:commons-lang3:3.13.0")
    api("org.projectlombok:lombok:1.18.30")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("io.jsonwebtoken:jjwt-api:0.12.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.2")
    api("com.alibaba:transmittable-thread-local:2.14.2")
    api("com.alibaba.fastjson2:fastjson2:2.0.41")
}