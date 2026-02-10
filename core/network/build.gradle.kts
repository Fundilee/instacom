plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.somila.network"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.androidx.core.ktx)

    // Dependency Injection
    api(libs.koin.android)
    api(libs.koin.core)

    api (libs.logging.interceptor)

    api (libs.retrofit)
    api (libs.retrofit2.kotlin.coroutines.adapter)
    api (libs.converter.name)
    api (libs.gson)
}