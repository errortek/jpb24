plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.aboutlibraries)
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("C:\\Users\\jpb\\Documents\\keys.jks")
            storePassword = "1223jpb?"
            keyAlias = "key0"
            keyPassword = "1223key0?"
        }
    }
    namespace = "com.jpb.jpb24"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jpb.jpb24"
        minSdk = 27
        targetSdk = 36
        versionCode = 20
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)  {
        exclude(group = "com.google.android.material", module = "material")
    }
    //implementation(libs.material)
    implementation(libs.androidx.constraintlayout)  {
        exclude(group = "com.google.android.material", module = "material")
    }
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)  {
        exclude(group = "com.google.android.material", module = "material")
    }
    implementation(libs.androidx.navigation.ui.ktx) {
        exclude(group = "com.google.android.material", module = "material")
    }
    implementation(files("C:\\Users\\jpb\\Downloads\\lib-release.aar"))
    implementation(libs.androidx.dynamicanimation)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.preference) {
        exclude(group = "com.google.android.material", module = "material")
    }
    implementation(libs.aboutlibraries.core)
    implementation(libs.aboutlibraries.ui) {
        exclude(group = "com.google.android.material", module = "material")
    }
    implementation(libs.androidx.activity) {
        exclude(group = "com.google.android.material", module = "material")
    }
    implementation(libs.androidx.browser) {
        exclude(group = "com.google.android.material", module = "material")
    }
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}