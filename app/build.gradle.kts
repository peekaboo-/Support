plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.mavenPublish)
}

android {
    namespace = "com.peekaboo.support"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.peekaboo.support"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    api(fileTree("libs") { include("*.jar", "*.aar") })

//    api(project(":Third"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    implementation("com.github.peekaboo-:Support:V1.0")
}

val GROUP_ID = "com.github.peekaboo-"
val ARTIFACT_ID = "Support"
val VERSION = latestGitTag().ifEmpty { "1.0.0-SNAPSHOT" }

fun latestGitTag(): String {
    val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0").start()
    return  process.inputStream.bufferedReader().use {bufferedReader ->
        bufferedReader.readText().trim()
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = GROUP_ID
                artifactId = ARTIFACT_ID
                version = VERSION
            }
        }
    }
}

//publishing { // 发布配置
//    publications { // 发布的内容
//        register<MavenPublication>("release") { // 注册一个名字为 release 的发布内容
//            groupId = GROUP_ID
//            artifactId = ARTIFACT_ID
//            version = VERSION
//
//            afterEvaluate { // 在所有的配置都完成之后执行
//                // 从当前 module 的 release 包中发布
//                from(components["release"])
//            }
//        }
//    }
//}