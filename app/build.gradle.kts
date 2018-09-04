import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(27)
    buildToolsVersion = "27.0.3"

    defaultConfig {
        applicationId = "com.droidcba.kedditbysteps"
        minSdkVersion(16)
        targetSdkVersion(27)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
        }
    }
    packagingOptions.exclude("META-INF/main.kotlin_module")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("com.android.support:appcompat-v7:27.1.1")
    implementation("com.android.support:design:27.1.1")
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    // Picasso
    implementation("com.squareup.picasso:picasso:2.5.2")

    // LiveData and ViewModel
    implementation("android.arch.lifecycle:extensions:1.1.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.4.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.0.0")

    // Dagger 2
    implementation("com.google.dagger:dagger:2.11")
    kapt("com.google.dagger:dagger-compiler:2.11")
    compileOnly("org.glassfish:javax.annotation:10.0-b28")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.23.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:0.23.0")
    // Coroutines - Retrofit extention
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-experimental-adapter:1.0.0")

    // Tests
    testImplementation("junit:junit:4.12")
    testImplementation("io.mockk:mockk:1.8.7")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.23.0")
    testImplementation("android.arch.core:core-testing:1.1.1")
    testImplementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
}

repositories {
    mavenCentral()
    maven("http://repository.jetbrains.com/all")
}
