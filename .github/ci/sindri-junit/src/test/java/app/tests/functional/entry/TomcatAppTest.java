/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.functional.entry;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import app.tests.fixtures.entry.RuntimeServerFixture;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.Test;

/**
 * End-to-end test for the embedded Tomcat runtime.
 *
 * <p>Starts the real application under Tomcat on a free port, makes a live {@code GET /}, and
 * asserts the welcome route rendered its response — exercising the whole per-request path (convert
 * the native servlet request, dispatch it, emit the response through the native servlet response).
 */
final class TomcatAppTest {

    @Test
    void servesTheWelcomeRouteOverHttp() throws Exception {
        assumeTrue(
                RuntimeServerFixture.isAvailable("org.apache.catalina.startup.Tomcat"),
                "Tomcat is not on the classpath.");

        int port = RuntimeServerFixture.freePort();
        Tomcat tomcat = app.http.TomcatApp.server(RuntimeServerFixture.configOnPort(port));

        try {
            RuntimeServerFixture.awaitPort(port);

            assertTrue(RuntimeServerFixture.get(port, "/").contains("Welcome!"));
        } finally {
            RuntimeServerFixture.stopQuietly(
                    () -> {
                        tomcat.stop();
                        tomcat.destroy();
                    });
        }
    }
}
