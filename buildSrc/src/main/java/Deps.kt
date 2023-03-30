object Deps {
    val androidGradlePlugin by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val hiltGradlePlugin by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }
    val kotlinGradlePlugin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }

    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activity}" }
    val activityKtx by lazy { "androidx.activity:activity-ktx:${Versions.activity}" }
    val foundationCompose by lazy { "androidx.compose.foundation:foundation:${Versions.compose}" }
    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
    val hiltNavigation by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }
    val materialCompose by lazy { "androidx.compose.material:material:${Versions.compose}" }
    val navigation by lazy { "androidx.navigation:navigation-compose:${Versions.navigation}" }
}
