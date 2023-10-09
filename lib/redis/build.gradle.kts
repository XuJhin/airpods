import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

fun plugins() {
    // 这里添加你的插件声明，例如 Spring Boot 插件
    // 例如：`id("org.springframework.boot") version "2.5.5"`
}
// 设置项目的归档基本名称
archivesName.set("redis")

// 取消打包为 bootJar 可独立运行包，因为 common 只是一个普通的 JAR 包
// 如果需要独立运行的 JAR 包，请将这个属性设置为 true
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
    archiveBaseName = "redis"
}


dependencies {
    implementation(project(":lib:common"))
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

}