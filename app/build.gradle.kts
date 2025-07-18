plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.traffbraza.funnycombination"
    compileSdk = project.properties["compileSdkVersion"]?.toString()?.toInt()

    defaultConfig {
        applicationId = "com.traffbraza.funnycombination"

        minSdk = project.properties["minSdkVersion"]?.toString()?.toInt()
        targetSdk = project.properties["targetSdkVersion"]?.toString()?.toInt()

        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
}

composeCompiler {
    stabilityConfigurationFiles.add(
        rootProject.layout.projectDirectory.file("stability_config.conf")
    )
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
}


dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.work)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    implementation(libs.compose.state.events)

    implementation(libs.androidx.spashscreen)
}
