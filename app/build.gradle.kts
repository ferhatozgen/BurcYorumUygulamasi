import java.util.Properties
import java.io.FileInputStream // <--- 1. ÇÖZÜM: Buraya import ekledik

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

// Local.properties dosyasını okuma işlemi
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    // Import ettiğimiz için artık başına java.io yazmamıza gerek yok
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "com.ferhatozgen.burcayapp"
    compileSdk = 34 // Not: release(36) bazen hata verebilir, genelde 34 veya 35 kullanılır. Hata alırsan burayı kontrol et.

    defaultConfig {
        applicationId = "com.ferhatozgen.burcayapp"
        minSdk = 24
        targetSdk = 34 // Burası da compileSdk ile uyumlu olmalı
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // 2. ÇÖZÜM: İkinci defaultConfig bloğunu buraya taşıdık (Birleştirdik)
        // Eğer local.properties içinde KEY yoksa hata vermemesi için boş kontrolü ekledim
        val apiKey = localProperties.getProperty("GEMINI_API_KEY") ?: ""
        buildConfigField("String", "GEMINI_API_KEY", "\"$apiKey\"")
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

    // 3. ÇÖZÜM: buildFeatures bloklarını birleştirdik
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Senin eklediklerin
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation("com.google.ai.client.generativeai:generativeai:0.2.0")
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}