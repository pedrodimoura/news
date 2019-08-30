import com.github.pedrodimoura.news.Dependencies
import com.github.pedrodimoura.news.config.AppConfiguration
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

}

dependencies {
    Dependencies.app.forEach { implementation(it) }
    Dependencies.appKapt.forEach { kapt(it) }
    Dependencies.test.forEach { testImplementation(it) }
    Dependencies.instrumented.forEach { androidTestImplementation(it) }
}
