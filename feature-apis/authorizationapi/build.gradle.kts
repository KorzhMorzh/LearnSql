plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
val configureAndroidOptions: Project.() -> Unit by rootProject.extra
configureAndroidOptions()

dependencies {
    implementation(project(":app-api"))

    implementation("androidx.core:core-ktx")
    implementation("androidx.appcompat:appcompat")
    implementation("com.google.android.material:material")
}