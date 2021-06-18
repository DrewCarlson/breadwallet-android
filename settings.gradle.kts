rootProject.name = "breadwallet-android"

include(
    "app",
    "app-core",
    "ui:ui-common",
    "ui:ui-navigation",
    "ui:ui-staking",
    "ui:ui-gift",
    "theme"
)

includeBuild("external/walletkit/WalletKitJava") {
    dependencySubstitution {
        substitute(module("com.breadwallet.core:corecrypto-android"))
            .with(project(":corecrypto-android"))
    }
}

includeBuild("external/redacted") {
    dependencySubstitution {
        substitute(module("dev.zacsweers.redacted:redacted-compiler-plugin"))
            .with(project(":redacted-compiler-plugin"))
        substitute(module("dev.zacsweers.redacted:redacted-compiler-plugin-annotations"))
            .with(project(":redacted-compiler-plugin-annotations"))
    }
}

includeBuild("external/redacted/redacted-compiler-gradle-plugin") {
    dependencySubstitution {
        substitute(module("dev.zacsweers.redacted:redacted-compiler-plugin-gradle"))
            .with(project(":"))
        substitute(module("dev.zacsweers.redacted:redacted-compiler-gradle-plugin"))
            .with(project(":"))
    }
}
