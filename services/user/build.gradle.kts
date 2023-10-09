import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {

}
// 设置项目的归档基本名称
archivesName.set("user")
dependencies {
    implementation(project(":lib:common"))
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

// 取消打包为 bootJar 可独立运行包，因为 common 只是一个普通的 JAR 包
// 如果需要独立运行的 JAR 包，请将这个属性设置为 true
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
    archiveBaseName = "user"
    archiveClassifier = "boot"
}

