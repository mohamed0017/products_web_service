plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "1.8.0"  // Add this line for serialization
    id("com.github.johnrengelman.shadow") version "7.1.0"  // Add this line for serialization
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("io.ktor:ktor-server-status-pages:2.3.0")
    implementation("io.ktor:ktor-server-call-logging:2.3.0")
    implementation("io.ktor:ktor-server-auth:2.3.0")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.0")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("org.jetbrains.kotlinx:atomicfu:0.21.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.postgresql:postgresql:42.6.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    // Define the main class for the application
    mainClass.set("io.ktor.server.netty.EngineMain")
}

tasks {
    // Create fat JAR with shadow plugin
    shadowJar {
        archiveFileName.set("build/libs/build/libs/build/libs/build/libs/products-1.0-SNAPSHOT.jar")  // Name of the fat JAR file
        manifest {
            attributes["Main-Class"] = "io.ktor.server.netty.EngineMain"  // Main class entry for running the JAR
        }
    }

    // Make sure `build` depends on the shadowJar task
    build {
        dependsOn(shadowJar)
    }
}


kotlin {
    jvmToolchain(17)
    tasks.jar {
        manifest {
            attributes(
                "Main-Class" to "org.example.MainKt" // Replace with your main class path
            )
        }
    }
}