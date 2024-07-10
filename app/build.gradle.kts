plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHiltAndroid)
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.testapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.com.google.dagger)
    ksp(libs.com.google.dagger.compiler)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.org.jetbrains.kotlinx)
    implementation(libs.androidx.databinding)
    implementation(libs.androidx.activity.ktx)

    //api calling
    implementation(libs.com.squareup.retrofit2)
    implementation(libs.com.squareup.retrofit2.gson)
    implementation(libs.com.squareup.retrofit2.scalars)
    implementation(libs.com.squareup.okhttp3)
    implementation(libs.com.squareup.okhttp3.interceptor)

}