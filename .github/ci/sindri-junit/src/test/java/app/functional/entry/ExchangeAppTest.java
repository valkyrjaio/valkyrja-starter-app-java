/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.functional.entry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.fixtures.entry.RuntimeServerFixture;
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
