buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.41")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
