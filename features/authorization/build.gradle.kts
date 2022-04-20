plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}
val configureAndroidOptions: Project.() -> Unit by rootProject.extra
configureAndroidOptions()
android {

}

dependencies {
    implementation(project(":authorizationapi"))
    implementation(project(":app-api"))

    implementation("androidx.core:core-ktx")
    implementation("androidx.appcompat:appcompat")
    implementation("com.google.android.material:material")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx")
    implementation("androidx.activity:activity-compose")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("com.google.dagger:dagger")
    kapt("com.google.dagger:dagger-compiler")
}