plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version Dependencies.Kotlin.version

}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSDK
        targetSdk = Config.targetSDK
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.version
    }

//    javaCompileOptions {
//        annotationProcessorOptions {
//            arguments += [
//                "room.schemaLocation":"$projectDir/schemas".toString(),
//                "room.incremental":"true",
//                "room.expandProjection":"true"
//            ]
//        }
//    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val accompanistVersion = "0.24.6-alpha"
val nav_version = "2.3.5"
val room_version = "2.3.0"
dependencies {
    implementation("com.github.skydoves:landscapist-glide:1.5.1")
    implementation("com.github.JamalMulla:ComposePrefs:1.0.2")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")



    implementation("com.google.accompanist:accompanist-flowlayout:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")

    implementation("androidx.paging:paging-compose:1.0.0-alpha14")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.2.0-alpha07")
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.4.0")
    // Compose Material Design
    implementation("androidx.compose.material:material:1.1.1")
    // Animations
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.1.1")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    // UI Tests


    implementation("androidx.navigation:navigation-compose:2.5.0-alpha04")


    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.23")


    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$room_version")

    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$room_version")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:2.5.0-alpha01")


    implementation("androidx.preference:preference-ktx:1.1.1")


    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")


    val hilt_version = "2.41"

    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-compiler:$hilt_version")
    kapt("com.google.dagger:hilt-android:$hilt_version")




    implementation("com.github.bumptech.glide:glide:4.12.0")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation(project(mapOf("path" to ":api")))
    implementation("androidx.room:room-ktx:2.3.0")
    implementation("androidx.collection:collection-ktx:")
    val paging_version = "3.0.1"

    implementation("androidx.paging:paging-runtime-ktx:$paging_version")

    val retrofit_version = "2.9.0"
    val moshi_version = "2.4.0"

    implementation("com.squareup.retrofit2:retrofit:${retrofit_version}")
    implementation("com.squareup.retrofit2:converter-moshi:${moshi_version}")
    implementation("com.squareup.moshi:moshi-adapters:1.5.0")

    implementation("androidx.core:core-ktx:${Dependencies.Kotlin.version}")


    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    val multidex_version = "2.0.1"
    implementation("androidx.multidex:multidex:$multidex_version")


    implementation("org.jetbrains.kotlin:kotlin-stdlib:$1.6.10")

    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
//    testImplementation "junit:junit:4.13.2"
//    androidTestImplementation "androidx.test.ext:junit:1.1.3"
//    androidTestImplementation "androidx.test.espresso:espresso-core:3.4.0"
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    val activity_version = "1.3.1"

    // Java language implementation
//    implementation("androidx.activity:activity:$activity_version")
    // Kotlin
    implementation("androidx.activity:activity-ktx:$activity_version")
    val fragment_version = "1.3.6"

    // Java language implementation
//    implementation("androidx.fragment:fragment:$fragment_version")
    // Kotlin
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
    // Testing Fragments in Isolation
//    debugImplementation("androidx.fragment:fragment-testing:$fragment_version")

    implementation("com.google.guava:guava:30.1.1-android")
//    implementation(Dependencies.Android.coreKtx)
//    implementation(Dependencies.Android.appCompat)
//    implementation(Dependencies.Android.material)
//
//    implementation(Dependencies.Compose.ui)
//    implementation(Dependencies.Compose.material)
//    implementation(Dependencies.Compose.tooling)
//    implementation(Dependencies.Compose.livedata)
//    implementation(Dependencies.Compose.accompanist)
//
//    implementation(Dependencies.Lifecycle.lifecycleKtx)
//    implementation(Dependencies.Lifecycle.viewModelCompose)
//    implementation(Dependencies.Lifecycle.activityCompose)
//    implementation(Dependencies.Navigation.navigationCompose)
//
//    implementation(Dependencies.Kotlin.serialization)
//
//    // Hilt
//    implementation(Dependencies.Hilt.android)
//    implementation(Dependencies.Hilt.navigation)
//    kapt(Dependencies.Hilt.compiler)
//
//    // Room
//    implementation(Dependencies.Room.ktx)
//    implementation(Dependencies.Room.runtime)
//    implementation(Dependencies.Room.paging)
//    kapt(Dependencies.Room.compiler)
//
//    testImplementation(Dependencies.Test.jUnit)
//    androidTestImplementation(Dependencies.Test.androidJUnit)
//    androidTestImplementation(Dependencies.Test.espresso)
//
//    androidTestImplementation(Dependencies.Compose.uiTest)
//    debugImplementation(Dependencies.Compose.toolingTest)
}