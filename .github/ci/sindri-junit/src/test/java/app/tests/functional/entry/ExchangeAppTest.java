/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.functional.entry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.tests.fixtures.entry.RuntimeServerFixture;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;

/**
 * End-to-end test for the built-in JDK runtime backing {@link app.http.App}.
 *
 * <p>Starts the real application on a free port, makes a live {@code GET /}, and asserts the
 * welcome route rendered its response. No availability check is needed — the JDK's {@code
 * com.sun.net.httpserver} is always present, which is why it is the zero-dependency default.
 */
final class ExchangeAppTest {

    @Test
    void servesTheWelcomeRouteOverHttp() throws Exception {
        int port = RuntimeServerFixture.freePort();
        HttpServer server = app.http.App.server(RuntimeServerFixture.configOnPort(port));

        try {
            RuntimeServerFixture.awaitPort(port);

            assertTrue(RuntimeServerFixture.get(port, "/").contains("Welcome!"));
        } finally {
            RuntimeServerFixture.stopQuietly(() -> server.stop(0));
        }
    }
}
