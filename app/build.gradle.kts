plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
    namespace = "home.replace_me"
    compileSdk = 35
    defaultConfig {
        applicationId = "home.replace_me"
        minSdk = 26
        targetSdk = 35
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
                storeType = 'PKCS12'
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }
    buildTypes {
        getByName("release") {
            applicationIdSuffix = getGitUserSuffix()
            signingConfig = signingConfigs.release
            isMinifyEnabled = false
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Or JavaVersion.VERSION_17 if preferred
        targetCompatibility = JavaVersion.VERSION_1_8 // Or JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "1.8" // Or "17"
    }
    buildFeatures {
        viewBinding = true // Enable ViewBinding for easier UI interaction
    }
}

dependencies {
    // Core libraries
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("com.google.android.material:material:1.12.0")

    // Testing libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
