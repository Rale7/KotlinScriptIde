import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("plugin.serialization") version "1.9.0"
}

group = "com.kotlin.script.ide"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.uiTestJUnit4)
    implementation(compose.desktop.currentOs)
    implementation("br.com.devsrsouza.compose.icons:simple-icons:1.1.1")
    implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.5.0")
    implementation("com.composables:icons-lucide:1.0.0")
    implementation("io.insert-koin:koin-core:3.4.3")
    implementation("io.insert-koin:koin-test:3.4.3")
    implementation("io.insert-koin:koin-test-junit4:3.4.3")
    implementation("io.insert-koin:koin-compose:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}

compose.desktop {
    application {
        mainClass = "com.kotlin.script.ide.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KotlinScriptIde"
            packageVersion = "1.0.0"
        }
    }
}
