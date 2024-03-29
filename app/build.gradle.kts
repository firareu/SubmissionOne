plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}

android {
    namespace = "com.example.submissionone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.submissionone"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
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
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.databinding:databinding-runtime:8.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation ("androidx.core:core-splashscreen:1.0.1")

    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    implementation ("androidx.fragment:fragment-ktx:1.6.1")

    //testing
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    //room
    implementation ("androidx.room:room-runtime:2.6.0-rc01")
    kapt ("androidx.room:room-compiler:2.6.0-rc01")
    //ksp("androidx.room:room-compiler:2.5.2")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    //coroutine support
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") //viewModelScope
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") //liveData
    implementation ("androidx.room:room-ktx:2.5.2")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")


    implementation ("com.google.code.gson:gson:2.9.0") // Sesuaikan versi sesuai kebutuhan

    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("androidx.datastore:datastore-preferences-core:1.0.0")

}

