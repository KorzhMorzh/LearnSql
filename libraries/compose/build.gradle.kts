plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val configureAndroidOptions: Project.() -> Unit by rootProject.extra
configureAndroidOptions()
val composeVersion: String by rootProject.extra

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
}

dependencies {
    implementation(project(":app-api"))

    implementation("androidx.core:core-ktx")
    implementation("androidx.appcompat:appcompat")
    implementation("com.google.android.material:material")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("io.coil-kt:coil-compose:2.0.0")
    implementation("com.jakewharton.timber:timber")
}