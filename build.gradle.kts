plugins {
    id("java")
}

group = "com.fazziclay.javaworldtools"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    //implementation("com.github.hollow-cube:minestom-ce:d47db72421")
    implementation("dev.hollowcube:polar:1.3.1")
    implementation("net.sf.jopt-simple:jopt-simple:6.0-alpha-3")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "com.fazziclay.javaworldtools.Main"
    val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree) // OR .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}