plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
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
    implementation(project(":authorizationapi"))
    implementation(project(":app-api"))
    implementation(project(":navigation-api"))
    implementation(project(":compose"))
    implementation(project(":authorization"))
    implementation(project(":course"))
    implementation(project(":methodic"))
    implementation(project(":task"))
    implementation(project(":settings"))
    implementation(project(":profile"))

    implementation("androidx.core:core-ktx")
    implementation("androidx.appcompat:appcompat")
    implementation("com.google.android.material:material")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx")
    implementation("androidx.fragment:fragment-ktx")
    implementation("androidx.activity:activity-compose")
    implementation("androidx.navigation:navigation-fragment-ktx")
    implementation("androidx.navigation:navigation-ui-ktx")
    implementation("com.google.dagger:dagger")
    kapt("com.google.dagger:dagger-compiler")
    implementation("com.jakewharton.timber:timber")
    implementation("com.squareup.retrofit2:retrofit")
    implementation("com.squareup.retrofit2:converter-gson")
    implementation("com.google.code.gson:gson")
}