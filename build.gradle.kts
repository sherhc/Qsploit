// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.72"
}
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
    }
}
allprojects {
    repositories {
        google()
        jcenter()
    }
}
tasks.named("clean", Delete::class) {
    delete(rootProject.buildDir)
}