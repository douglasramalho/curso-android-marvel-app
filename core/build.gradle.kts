plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("dev.detekt")
}

apply(from = rootProject.file("config/detekt/detekt.gradle"))

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
