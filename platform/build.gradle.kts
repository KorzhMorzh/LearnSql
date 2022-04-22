plugins {
    id("java-platform")
}

val kotlinVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra

dependencies {
    constraints {
        api("androidx.activity:activity-compose:1.4.0")
        api("androidx.activity:activity-ktx:1.4.0")
        api("androidx.annotation:annotation:1.3.0")
        api("androidx.appcompat:appcompat:1.4.1")
        api("androidx.compose.animation:animation:$composeVersion")
        api("androidx.compose.foundation:foundation:$composeVersion")
        api("androidx.compose.material:material:$composeVersion")
        api("androidx.compose.runtime:runtime-livedata:$composeVersion")
        api("androidx.compose.ui:ui-tooling:$composeVersion")
        api("androidx.compose.ui:ui-tooling-preview:$composeVersion")
        api("androidx.compose.ui:ui-test-junit4:$composeVersion")
        api("androidx.compose.ui:ui-test:$composeVersion")
        api("androidx.compose.ui:ui-test-junit4:$composeVersion")
        api("com.google.accompanist:accompanist-insets-ui:0.20.2")
        api("androidx.core:core-ktx:1.7.0")
        api("androidx.fragment:fragment-ktx:1.4.0")
        api("androidx.lifecycle:lifecycle-runtime:2.4.0")
        api("androidx.lifecycle:lifecycle-common-java8:2.4.0")
        api("androidx.lifecycle:lifecycle-extensions:2.2.0")
        api("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
        api("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
        api("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
        api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
        api("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.4.0")
        api("androidx.navigation:navigation-fragment-ktx:2.4.2")
        api("androidx.navigation:navigation-ui-ktx:2.4.2")
        api("androidx.transition:transition:1.4.1")
        api("com.github.bumptech.glide:compiler:4.12.0")
        api("com.github.bumptech.glide:glide:4.12.0")
        api("com.github.bumptech.glide:okhttp3-integration:4.12.0")
        api("com.google.android.material:material:1.5.0")
        api("com.google.android.play:core-ktx:1.8.1")
        api("com.google.android.play:core:1.10.3")
        api("com.google.code.gson:gson:2.8.9")
        api("com.google.dagger:dagger-compiler:2.40.3")
        api("com.google.dagger:hilt-android:2.40.3")
        api("com.google.dagger:hilt-android-compiler:2.40.3")
        api("com.google.dagger:dagger:2.40.3") { because("check https://github.com/google/dagger/issues/3090 before update to 2.40.5") }
        api("com.jakewharton.timber:timber:5.0.1")
        api("com.squareup.okhttp3:logging-interceptor:4.9.3")
        api("com.squareup.okhttp3:okhttp:4.9.3")
        api("com.squareup.retrofit2:converter-gson:2.9.0")
        api("com.squareup.retrofit2:converter-scalars:2.9.0")
        api("com.squareup.retrofit2:retrofit:2.9.0")
        api("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        api("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
        api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
        api("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.2")
        api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    }
}