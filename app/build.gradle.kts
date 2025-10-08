import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val localProps = Properties().apply {
    val f = rootProject.file("local.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}
fun resolveSecret(name: String): String? =
    localProps.getProperty(name)
        ?: (findProperty(name) as String?)
        ?: System.getenv(name)

android {
    namespace = "com.example.nba"
    compileSdk = 35

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }


    defaultConfig {
        applicationId = "com.example.nba"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.1"

        buildConfigField(
            "String", "BALLDONTLIE_API_KEY",
            "\"${resolveSecret("BALLDONTLIE_API_KEY") ?: "CHANGE_ME"}\""
        )
        buildConfigField(
            "String", "THESPORTSDB_API_KEY",
            "\"${resolveSecret("THESPORTSDB_API_KEY") ?: "CHANGE_ME"}\""
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.14" }

    kotlinOptions { jvmTarget = "17" }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(platform(libs.androidx.compose.bom.v20250901))
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)
    implementation(libs.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.landscapist.glide)
}
