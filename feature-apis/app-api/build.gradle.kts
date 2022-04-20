plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
}

val configureAndroidOptions: Project.() -> Unit by rootProject.extra
configureAndroidOptions()

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    implementation("androidx.appcompat:appcompat")
    implementation("androidx.fragment:fragment-ktx")

    implementation("com.squareup.retrofit2:retrofit")
    implementation("com.squareup.retrofit2:converter-gson")
    implementation("com.squareup.okhttp3:logging-interceptor")
}
