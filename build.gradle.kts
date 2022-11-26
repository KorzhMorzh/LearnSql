import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

buildscript {

    val kotlinVersion by extra("1.7.10")
    val composeVersion by extra("1.3.1")

    val addBuildScriptDependencies by extra(
        fun ScriptHandlerScope.() {
            dependencies {
                classpath("com.android.tools.build:gradle:7.3.1")
                classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
                classpath(kotlin("gradle-plugin", version = kotlinVersion))
                classpath(kotlin("serialization", version = kotlinVersion)) // Must be here (not in settings.gradle.kts) for the IDE to detect it
            }
        }
    )
    addBuildScriptDependencies()
}

val configureAndroidOptions by extra(
    fun Project.() {
        extensions.configure<BaseExtension> {
            compileSdkVersion(33)

            defaultConfig {
                minSdk = 21
                targetSdk = 33

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            (this as ExtensionAware).extensions.findByType<KotlinJvmOptions>()?.let {
                it.jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }

        dependencies {
            for (configName in listOf(
                "implementation",
                "androidTestImplementation",
                "kapt",
                "kaptTest",
                "kaptAndroidTest",
                "compileOnly",
                "coreLibraryDesugaring"
            )) {
                val configuration = configurations.findByName(configName) ?: continue
                configuration.dependencies.add(platform(project(":platform")))
                configuration.dependencies.add(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.6.0"))
            }
        }
    }
)
plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}