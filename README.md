<p align="center"><a href="https://valkyrja.io" target="_blank">
    <img src="https://raw.githubusercontent.com/valkyrjaio/art/refs/heads/master/long-banner/orange/java.png" width="100%">
</a></p>

# Valkyrja Starter (App)

Starter template for building Java applications on the
[Valkyrja][Valkyrja url] framework.

This repository gives you a working Valkyrja application as a starting point —
HTTP and CLI kernels pre-wired, example controllers and commands, configuration
scaffolding, and a ready-to-customize `app` package. The starter passes the
same formatting, static analysis, and architectural rules as the Valkyrja
framework itself, so you can focus on building your application rather than
cleaning up the foundation.

<p>
    <a href="https://github.com/valkyrjaio/application-java"><img src="https://img.shields.io/badge/Java-21--25-orange" alt="Java Version"></a>
    <a href="https://github.com/valkyrjaio/application-java/blob/26.x/LICENSE.md"><img src="https://img.shields.io/badge/license-MIT-blue" alt="License"></a>
    <a href="https://github.com/valkyrjaio/application-java/actions/workflows/ci.yml?query=branch%3A26.x"><img src="https://github.com/valkyrjaio/application-java/actions/workflows/ci.yml/badge.svg?branch=26.x" alt="CI Status"></a>
    <a href="https://sonarcloud.io/summary/new_code?id=valkyrjaio_application-java"><img src="https://sonarcloud.io/api/project_badges/measure?project=valkyrjaio_application-java&metric=sqale_rating" alt="Maintainability Rating"></a>
</p>

What's in the Box
-----------------

- **Pre-wired HTTP and CLI kernels** — the application boots and responds to
  both web requests and command-line invocations out of the box
- **Example controllers and commands** — working code showing typical routing,
  request handling, and command dispatch patterns
- **Configuration scaffolding** — `Config` classes and `data` packages with
  example files and environment-driven overrides
- **Testing setup** — JUnit configured with the same structure used across
  Valkyrja's own components
- **Full CI pipeline** — Spotless, ArchUnit, Error Prone, SpotBugs, and JUnit
  all configured and passing on a clean clone
- **Persistent worker entry points** _(optional)_ — embedded Tomcat, Jetty, or
  Netty for production-grade deployments

Installation
------------

### Use this template _(recommended)_

This repository is a GitHub template. Click the **Use this template** button
at the top of the repo to create a new repository in your own account,
pre-populated with the starter code.

### Clone manually _(for contributing to the starter itself)_

```
git clone git@github.com:valkyrjaio/application-java.git
cd application-java
./gradlew build
```

Getting Started
---------------

### Project Structure

The key directories you'll work in:

```
app/
├── src/main/java/app/      # your application code lives here
│   ├── cli/                # CLI commands, controllers, providers, and data
│   └── http/               # HTTP controllers, providers, and data
├── bin/cli                 # CLI entry-point launcher
└── public/index            # HTTP entry-point launcher
```

Your application code goes in the `app` package under `app/src/main/java/app/`.
The starter provides example HTTP controllers and CLI commands you can study,
modify, or replace.

### Running Your Application

Build the application jar first:

```
./gradlew build
```

**HTTP:**

```
./app/public/index
```

Navigate to `http://localhost:8080` to see the example routes.

**CLI:**

```
./app/bin/cli
```

Run with no arguments to see the list of available commands.

### Writing Code

**Adding a route:** see the example controller in
`app/src/main/java/app/http/controller/` and the `RouteProvider` that registers
it. For the full routing API, see the
[Valkyrja HTTP documentation][http docs url].

**Adding a command:** see the example command in
`app/src/main/java/app/cli/command/`. For the full CLI API, see the
[Valkyrja CLI documentation][cli docs url].

**Binding services:** the dependency injection container is configured in the
`provider` packages under each `app` subpackage. See the
[Valkyrja container documentation][container docs url] for the full API.

### Running Tests

```
./gradlew junit
```

### Running CI Checks Locally

The starter ships with the same CI pipeline as the Valkyrja framework. Run
any check via its Gradle task:

```
./gradlew spotlessCheck
./gradlew archunit
./gradlew errorprone
./gradlew spotbugs
./gradlew junit
```

Or run the full pipeline at once:

```
./gradlew ci
```

Deployment
----------

The starter runs on any Java 21+ runtime. For production, Valkyrja provides
persistent-worker entry points that bootstrap the application once and then
serve requests with per-request container isolation:

- [**Tomcat**][tomcat url] — embedded Apache Tomcat persistent worker
- [**Jetty**][jetty url] — embedded Eclipse Jetty persistent worker
- [**Netty**][netty url] — Netty-based persistent worker

See each integration's README for setup instructions specific to that runtime.

Documentation
-------------

Full Valkyrja documentation lives in the [framework repository][docs url] and
is baked into the source tree so you can browse it offline.

For starter-specific questions, open an issue on this repository. For
framework questions, open an issue on the
[Valkyrja framework repository][framework url].

Contributing
------------

Contributions to the starter itself — improvements to the example code,
bug fixes, CI improvements — are welcome. See
[`CONTRIBUTING.md`][contributing url] for the submission process and
[`VOCABULARY.md`][vocabulary url] for the terminology used across Valkyrja.

License
-------

The Valkyrja framework and this starter are open-source software licensed
under the [MIT license][MIT license url]. See [`LICENSE.md`](./LICENSE.md).

[Valkyrja url]: https://valkyrja.io

[framework url]: https://github.com/valkyrjaio/valkyrja-java

[tomcat url]: https://github.com/valkyrjaio/tomcat

[jetty url]: https://github.com/valkyrjaio/jetty

[netty url]: https://github.com/valkyrjaio/netty

[docs url]: https://github.com/valkyrjaio/valkyrja-java/tree/26.x/src/main/java/io/valkyrja/README.md

[http docs url]: https://github.com/valkyrjaio/valkyrja-java/tree/26.x/src/main/java/io/valkyrja/http

[cli docs url]: https://github.com/valkyrjaio/valkyrja-java/tree/26.x/src/main/java/io/valkyrja/cli

[container docs url]: https://github.com/valkyrjaio/valkyrja-java/tree/26.x/src/main/java/io/valkyrja/container

[contributing url]: https://github.com/valkyrjaio/.github/blob/master/CONTRIBUTING.md

[vocabulary url]: https://github.com/valkyrjaio/.github/blob/master/VOCABULARY.md

[MIT license url]: https://opensource.org/licenses/MIT
