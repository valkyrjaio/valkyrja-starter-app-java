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
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * End-to-end test for the Jetty gRPC runtime.
 *
 * <p>Starts the real application under {@code app.grpc.JettyApp}'s own server — the exact one its
 * blocking {@code run(...)} builds, a grpc-servlet mounted on embedded Jetty — on an ephemeral
 * port, confirms it came up, then stops it. That proves the application bootstraps and the
 * grpc-servlet transport assembles against it; the framework's own smoke tests cover the same
 * ground, since exercising a call needs generated stubs the application does not ship.
 */
@Timeout(30)
final class JettyGrpcAppTest {

    @Test
    void bootsTheGrpcServerOverJetty() throws Exception {
        assumeTrue(
                RuntimeServerFixture.isAvailable("io.grpc.servlet.jakarta.ServletServerBuilder"),
                "The grpc-servlet transport is not on the classpath.");

        Server server = app.grpc.JettyApp.server(RuntimeServerFixture.grpcConfigOnPort(0));

        try {
            assertTrue(server.isStarted());
        } finally {
            RuntimeServerFixture.stopQuietly(server::stop);
        }
    }
}
