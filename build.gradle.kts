import java.io.BufferedReader
import java.io.InputStreamReader

plugins {
    id("java")
    id("maven-publish")
}

group = "dev.rgbmc"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    implementation("com.github.cryptomorin:XSeries:13.0.0")
}

tasks.compileJava {
    options.release = 8
    options.encoding = "UTF-8"
}

publishing {
    repositories {
        maven("https://repo.fastmcmirror.org/content/repositories/releases/") {
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = "dev.rgbmc"
            artifactId = "SimpleInventory"
            version = this.version + "-" + id()
        }
    }
}

fun id(): String{
    val process = Runtime.getRuntime().exec("git rev-parse --short HEAD")
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val cid = reader.readLine()
    if (cid==null) {
        println("Failed get commit id")
        System.exit(0)
    }
    println("Commit ID: " + cid)
    return cid;
}