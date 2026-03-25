import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "ru.arturmineev9.avitotraineeassignment.core.network"
    compileSdk {
        version = release(libs.versions.compileSdk.get().toInt())
    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()

            val authKey = localProperties.getProperty("GIGACHAT_AUTH_KEY") ?: ""
            val authBaseUrl = localProperties.getProperty("GIGACHAT_AUTH_BASE_URL") ?: ""
            val apiBaseUrl = localProperties.getProperty("GIGACHAT_API_BASE_URL") ?: ""

            buildConfigField("String", "GIGACHAT_AUTH_KEY", "\"$authKey\"")
            buildConfigField("String", "GIGACHAT_AUTH_BASE_URL", "\"$authBaseUrl\"")
            buildConfigField("String", "GIGACHAT_API_BASE_URL", "\"$apiBaseUrl\"")

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }


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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.retrofit.core)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization
    )
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.compose)
}
