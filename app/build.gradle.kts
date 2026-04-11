plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

fun getGitUserSuffix(): String {
    try {
        val process = ProcessBuilder("git", "remote", "get-url", "origin").start()
        val remoteUrl = process.inputStream.bufferedReader().readText().trim()
        process.waitFor()
        // Regex for git@github.com:owner/repo and https://github.com/owner/repo capturing the owner
        val regex = Regex("[:/]([^/]+)/[^/]+(\\.git)?/?$")
        val matchResult = regex.find(remoteUrl)
        return matchResult?.groups?.get(1)?.value?.lowercase()?.let { ".$it" } ?: ".local"
    } catch (e: Exception) {
        println("Could not get git user: ${e.message}")
        return ".local"
    }
}

android {
    namespace = "home.app"
    compileSdk = 36
    defaultConfig {
        applicationId = "home.replace_me"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    // Exclude dependencies info to fix "Problem: found extra signing block 'Dependency metadata'" in F-Droid.
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
    signingConfigs {
        create("release") {
            if (System.getenv("KEYSTORE_PATH") != null) {
                storeFile = file(System.getenv("KEYSTORE_PATH"))
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                storeType = "PKCS12"
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }
    buildTypes {
        getByName("release") {
            applicationIdSuffix = getGitUserSuffix()
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true // Enable ViewBinding for easier UI interaction
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    // Core libraries
    implementation("androidx.core:core-ktx:1.18.0")
    implementation("com.google.android.material:material:1.13.0")

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2026.03.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.13.0")

    // Testing libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
