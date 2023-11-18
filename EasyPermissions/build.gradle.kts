plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.hopcape.easypermissions"
    compileSdk = 33

    defaultConfig {
        minSdk = 25

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    sourceSets {
        getByName("main") {
            res {
                srcDirs("src\\main\\res", "src\\main\\res")
            }
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}

// Assuming you're working within the `plugins` and other configurations

tasks.register("publishing") {
    afterEvaluate {
        publishing {
            repositories {
                maven {
                    name = "jitpack"
                    url = uri("https://jitpack.io")
                }
            }
            publications {
                create<MavenPublication>("release") {
                    from(components["release"])

                    groupId = "com.github.Aumaidkh"
                    artifactId = "easy-permission"
                    version = "1.0"
                }
            }
        }
    }
}
