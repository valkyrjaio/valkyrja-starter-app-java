/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.functional.entry;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import app.tests.fixtures.entry.RuntimeServerFixture;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.Test;

/**
 * End-to-end test for the Jetty runtime.
 *
 * <p>Starts the real application under Jetty on a free port, makes a live {@code GET /}, and
 * asserts the welcome route rendered its response — exercising the whole per-request path (convert
 * the native request, dispatch it, emit the response through the native Jetty response).
 */
final class JettyAppTest {

    @Test
    void servesTheWelcomeRouteOverHttp() throws Exception {
        assumeTrue(
                RuntimeServerFixture.isAvailable("org.eclipse.jetty.server.Server"),
                "Jetty is not on the classpath.");

        int port = RuntimeServerFixture.freePort();
        Server server = app.http.JettyApp.server(RuntimeServerFixture.configOnPort(port));

        try {
            RuntimeServerFixture.awaitPort(port);

            assertTrue(RuntimeServerFixture.get(port, "/").contains("Welcome!"));
        } finally {
            RuntimeServerFixture.stopQuietly(server::stop);
        }
    }
}
