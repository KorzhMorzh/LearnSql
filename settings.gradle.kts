import java.nio.file.Files

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "LearnSql"

include(":app")
include(":platform")

val rootDir = rootProject.projectDir
listOf("features", "feature-apis").forEach { dirName ->
    Files.list(rootDir.resolve(dirName).toPath())
        .filter { Files.isDirectory(it) }
        .forEach { module ->
            val moduleName = module.fileName
            val gradleFile = module.resolve("build.gradle.kts")
            if (Files.exists(gradleFile)) {
                include(":$moduleName")
                project(":$moduleName").projectDir = module.toFile().relativeTo(rootDir)
            } else {
                module.toFile().deleteRecursively()
            }
        }
}
