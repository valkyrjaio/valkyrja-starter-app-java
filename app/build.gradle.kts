/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

plugins {
    java
    application
}

sourceSets {
    main {
        java {
            exclude("**/*.example.java")
        }
    }
}

tasks.jar {
    archiveFileName.set("app.jar")
    manifest {
        attributes("Main-Class" to "app.http.App")
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// Sindri — the AST-based code generator (Maven Central: io.valkyrja:sindri). It is a
// *development tool only*: the application needs the generated App*Data files at runtime,
// not sindri itself, so it lives in its own configuration and never touches the runtime or
// compile classpath. This mirrors PHP's `require-dev` + `vendor/bin/sindri`.
//
// Run `./gradlew sindri` to regenerate every App*Data file, or `./gradlew sindriHttp` /
// `./gradlew sindriCli` to regenerate a single config's data. Sindri parses source syntactically;
// the valkyrja sources jar is added so it can also resolve framework providers (referenced from
// the app's ComponentProvider) and collect their publishers into AppContainerData.
val sindri by configurations.creating

dependencies {
    sindri("io.valkyrja:sindri:26.4.25")
    sindri("io.valkyrja:valkyrja:26.9.12:sources")

    // Runtime SDKs for the worker entry points (app.http.{Jetty,Netty,Tomcat}App).
    // The framework declares these compileOnly — the "optional adapter" philosophy — so each
    // consumer pulls only the runtime it actually uses. The JDK-backed app.http.App (ExchangeHttp)
    // needs none of them.
    implementation("org.eclipse.jetty:jetty-server:12.1.12")
    implementation("io.netty:netty-codec-http:4.2.17.Final")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.25")

    // gRPC transports for the gRPC worker entry points (app.grpc.{Jetty,Netty,Tomcat}App). The
    // framework keeps io.grpc compileOnly, so the application supplies the transport it uses;
    // the servlet transport also needs Jetty's ee10 servlet layer.
    implementation("io.grpc:grpc-api:1.83.1")
    implementation("io.grpc:grpc-servlet-jakarta:1.83.1")
    implementation("io.grpc:grpc-netty-shaded:1.83.1")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.1.12")
}

val sindriConfigs =
    mapOf(
        "Http" to "src/main/java/app/http/Config.java",
        "Cli" to "src/main/java/app/cli/Config.java",
        "Grpc" to "src/main/java/app/grpc/Config.java",
    )

val sindriTasks =
    sindriConfigs.map { (name, configPath) ->
        tasks.register<JavaExec>("sindri$name") {
            group = "sindri"
            description = "Regenerate the $name App*Data files from $configPath via sindri"
            classpath = sindri
            mainClass.set("io.sindri.Sindri")
            // Run from the module root so sindri resolves the config and writes the generated
            // data relative to it (its CliConfig reads System.getProperty("user.dir")).
            workingDir = projectDir
            args("generate", configPath)
        }
    }

tasks.register("sindri") {
    group = "sindri"
    description = "Regenerate all App*Data files (HTTP + CLI + gRPC) via sindri"
    dependsOn(sindriTasks)
}
