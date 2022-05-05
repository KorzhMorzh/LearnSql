import kotlin.io.println

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

val configureAndroidOptions: Project.() -> Unit by rootProject.extra
configureAndroidOptions()
val composeVersion: String by rootProject.extra

android {
    defaultConfig {
        applicationId = "ru.learnsql.mobile"
        versionName = "1.0"
        versionCode = 1
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation("androidx.core:core-ktx")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("com.google.android.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx")
    implementation("androidx.activity:activity-compose")
    implementation("androidx.appcompat:appcompat")
    implementation("androidx.activity:activity-ktx")
    implementation("com.google.code.gson:gson")
    kapt("com.google.dagger:dagger-compiler")
    implementation("com.google.dagger:dagger")
    debugImplementation("androidx.compose.ui:ui-tooling")

//    implementation("com.google.dagger:hilt-android")
//    kapt("com.google.dagger:hilt-android-compiler")

    rootProject.childProjects.filterKeys { it != "app" && it != "unittest"}.values.forEach { childProject ->
        println(childProject)
        implementation(childProject)
    }
}
