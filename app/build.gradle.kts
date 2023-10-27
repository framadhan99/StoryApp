plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs")
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.fajar.submissionstoryapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.fajar.submissionstoryapp"
        minSdk = 21
        targetSdk = 33
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
        freeCompilerArgs  += "-Xopt-in=kotlin.RequiresOptIn"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation ("com.github.bumptech.glide:glide:4.13.1")
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")

    // lifecycle
    val lifecycle_version = "2.5.1"
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")

    //navigation
    val nav_version = "2.5.3"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("androidx.paging:paging-runtime:3.1.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //room
    val room_version = "2.5.0"
    kapt ("androidx.room:room-compiler:2.4.2")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-paging:$room_version")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation ("com.google.dagger:hilt-android:2.41")
    kapt ("com.google.dagger:hilt-compiler:2.41")
    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.41")
    kaptTest ("com.google.dagger:hilt-compiler:2.41")

    //datastore
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
}