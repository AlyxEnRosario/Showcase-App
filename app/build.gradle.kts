plugins {
    alias(libs.plugins.android.application)
    // id("com.google.gms.google-services")  // Using local leaderboard instead
}

android {
    namespace = "com.example.androidportfolio"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.androidportfolio"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    
    // Retrofit & JSON
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)
    
    // Glide for image loading
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    
    // ViewPager2
    implementation(libs.viewpager2)
    
    // Preferences
    implementation(libs.preference)
    
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}