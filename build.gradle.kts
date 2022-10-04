buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Deps.androidGradlePlugin)
        classpath(Deps.kotlinGradlePlugin)
        classpath(Deps.hiltGradlePlugin)
    }
}