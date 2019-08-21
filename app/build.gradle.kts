import com.github.pedrodimoura.news.Libraries
import com.github.pedrodimoura.news.config.AppConfiguration

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(AppConfiguration.compileSdkVersion)
    buildToolsVersion(AppConfiguration.buildToolsVersion)
    defaultConfig {
        minSdkVersion(AppConfiguration.minSdkVersion)
        targetSdkVersion(AppConfiguration.targetSdkVersion)
        applicationId = AppConfiguration.applicationId
        versionCode = AppConfiguration.versionCode
        versionName = AppConfiguration.versionName
        testInstrumentationRunner = AppConfiguration.testInstrumentationRunner
    }

    buildTypes {
        getByName(AppConfiguration.releaseBuildTypeName) {
            isMinifyEnabled = AppConfiguration.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile(AppConfiguration.proguardAndroidOptimize), AppConfiguration.proguardRules)
        }
    }
}

dependencies {
    Libraries.app.forEach { implementation(it) }
    Libraries.appKapt.forEach { kapt(it) }
    Libraries.test.forEach { testImplementation(it) }
    Libraries.instrumented.forEach { androidTestImplementation(it) }
}
