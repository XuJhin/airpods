import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {

}
// 设置项目的归档基本名称
archivesName.set("community")
dependencies {
    implementation(project(":lib:common"))
    implementation(project(":lib:redis"))
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-sentinel")
}

// 取消打包为 bootJar 可独立运行包，因为 common 只是一个普通的 JAR 包
// 如果需要独立运行的 JAR 包，请将这个属性设置为 true
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
    archiveBaseName = "community"
    archiveClassifier = "boot"
}

