plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.s17.escopete.stevenerrol.arpeggeo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.s17.escopete.stevenerrol.arpeggeo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        /* Credentials */
        buildConfigField("String", "SPOTIFY_CLIENT_ID", "\"${project.findProperty("SPOTIFY_CLIENT_ID")}\"")
        buildConfigField("String", "SPOTIFY_REDIRECT_URI", "\"${project.findProperty("SPOTIFY_REDIRECT_URI")}\"")
        buildConfigField("String", "SPOTIFY_AUTH_SCOPES", "\"${project.findProperty("SPOTIFY_AUTH_SCOPES")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        buildFeatures {
            buildConfig = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation ("com.spotify.android:auth:1.2.5")
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(fileTree(mapOf(
        "dir" to "libs",
        "include" to listOf("*.aar", "*.jar")
    )))
    implementation ("com.google.code.gson:gson:2.10.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation ("com.squareup.inject:assisted-inject-annotations-dagger2:0.6.0")
    kapt ("com.squareup.inject:assisted-inject-processor-dagger2:0.6.0")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("com.github.MKergall:osmbonuspack:6.9.0")
    implementation("org.osmdroid:osmdroid-android:6.1.20")
    implementation("com.google.android.material:material:1.12.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

kapt {
    correctErrorTypes = true
}