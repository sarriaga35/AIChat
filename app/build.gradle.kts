plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
}

android {
    namespace = "com.cursokotlin.aichat"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cursokotlin.aichat"
        minSdk = 28
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        
        // Disable the deprecation warning for Hilt's generated code
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    
    // Add ViewModel and Lifecycle dependencies
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Add Hilt Navigation Compose dependency
    implementation(libs.androidx.hilt.navigation.compose)

    // Network - Retrofit, OkHttp, Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)
    
    // Add javax.annotation for Hilt's @Generated annotation
    implementation(libs.javax.annotation.api)
    
    // Add desugar for API compatibility
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    //Dagger - Hilt
    implementation (libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp (libs.dagger.compiler) // Dagger compiler
    ksp (libs.hilt.compiler)   // Hilt compiler
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Suppress Hilt generated code deprecation warnings
tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:-deprecation")
}