/*
 * This file is part of the Valkyrja Framework package.
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
import io.netty.channel.Channel;
import org.junit.jupiter.api.Test;

/**
 * End-to-end test for the Netty runtime.
 *
 * <p>Starts the real application under Netty on a free port, makes a live {@code GET /}, and
 * asserts the welcome route rendered its response — exercising the whole per-request path (convert
 * the native request, dispatch it, emit the response through the native Netty channel).
 */
final class NettyAppTest {

    @Test
    void servesTheWelcomeRouteOverHttp() throws Exception {
        assumeTrue(
                RuntimeServerFixture.isAvailable("io.netty.channel.Channel"),
                "Netty is not on the classpath.");

        int port = RuntimeServerFixture.freePort();
        Channel channel = app.http.NettyApp.server(RuntimeServerFixture.configOnPort(port));

        try {
            RuntimeServerFixture.awaitPort(port);

            assertTrue(RuntimeServerFixture.get(port, "/").contains("Welcome!"));
        } finally {
            // Closing the channel also shuts down the boss/worker event-loop groups.
            RuntimeServerFixture.stopQuietly(() -> channel.close().sync());
        }
    }
}
