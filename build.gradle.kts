plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.kotzilla) apply false
    alias(libs.plugins.detekt) apply false
}

subprojects {
    pluginManager.apply("io.gitlab.arturbosch.detekt")

    extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        allRules = false
        ignoreFailures = true
        config.setFrom(rootProject.files("detekt.yml"))
    }

    dependencies {
        // Избегаем обращения к version catalog здесь, чтобы не падать на ранних стадиях конфигурации
        add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
    }
}
