plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.plcoding.run.network"

}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.core.data)
}