plugins {
    id("com.android.application") version "8.10.1" apply false
    id("org.jetbrains.kotlin.android") version "2.1.21" apply false
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}
