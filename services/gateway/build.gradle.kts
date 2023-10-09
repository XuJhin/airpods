import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {

}
// 设置项目的归档基本名称
archivesName.set("gateway")
dependencies {
    implementation(project(":lib:common")) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-web")
        exclude(group = "org.springframework.boot", module = "spring-cloud-starter-bootstrap")
    }
    implementation(project(":lib:redis"))
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    implementation("io.jsonwebtoken:jjwt-api:0.12.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.2")
}

// 取消打包为 bootJar 可独立运行包，因为 common 只是一个普通的 JAR 包
// 如果需要独立运行的 JAR 包，请将这个属性设置为 true
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
    archiveBaseName = "gateway"
    archiveClassifier = "boot"
}

