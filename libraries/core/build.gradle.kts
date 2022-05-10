plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

val configureAndroidOptions: Project.() -> Unit by rootProject.extra
configureAndroidOptions()

dependencies {

    implementation("androidx.core:core-ktx")
    implementation("androidx.appcompat:appcompat")
    implementation("com.google.android.material:material")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.retrofit2:converter-gson")
    implementation("com.squareup.retrofit2:retrofit")
    implementation("androidx.compose.ui:ui")
    implementation("com.google.dagger:dagger")
    implementation(project(":app-api"))
    kapt("com.google.dagger:dagger-compiler")
}