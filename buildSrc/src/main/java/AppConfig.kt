import org.gradle.api.JavaVersion

// App level config constants
object AppConfig {
    const val compileSdk = 32
    const val minSdk = 23
    const val targetSdk = 32
    const val applicationId = "com.abhishek.pathak.kotlin.android.githubcompose"
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"

    val javaJvmTarget = JavaVersion.VERSION_11.toString()
    val javaCompatibility = JavaVersion.VERSION_11
}