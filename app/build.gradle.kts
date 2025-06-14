plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "home.replace_me"
    compileSdk = 35
    defaultConfig {
        applicationId = "home.replace_me"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false // Set to true for production releases with ProGuard/R8
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro" // Create this file if you enable minifyEnabled
            )
        }
        debug {
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
