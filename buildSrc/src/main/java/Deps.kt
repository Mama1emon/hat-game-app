object Deps {
    val androidGradlePlugin by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlinGradlePlugin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val hiltGradlePlugin by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }
    val activityKtx by lazy { "androidx.activity:activity-ktx:${Versions.activity}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activity}" }
    val materialCompose by lazy { "androidx.compose.material:material:${Versions.compose}" }
    val foundationCompose by lazy { "androidx.compose.foundation:foundation:${Versions.compose}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
}