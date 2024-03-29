plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.androidtest.bymilanchothani"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.androidtest.bymilanchothani"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true


        testInstrumentationRunner = "com.androidtest.bymilanchothani.di.CustomTestRunner"


        buildConfigField("String", "API_URL", "\"https://cube-academy-api.cubeapis.com/\"")
        buildConfigField("String", "AUTH_TOKEN", "\"${project.property("authToken")}\"")

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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Responsive lib for support all size device
    implementation ("com.intuit.sdp:sdp-android:1.1.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("com.android.support:multidex:1.0.3")


    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    testImplementation("com.google.dagger:hilt-android-testing:2.48")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptTest("com.google.dagger:hilt-android-compiler:2.48")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")

    implementation ("androidx.room:room-runtime:2.6.0")
    implementation ("androidx.room:room-ktx:2.6.0")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    kapt("androidx.room:room-compiler:2.6.0")
}