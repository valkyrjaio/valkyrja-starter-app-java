/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.functional.entry;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import app.tests.fixtures.entry.RuntimeServerFixture;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * End-to-end test for the embedded Tomcat gRPC runtime.
 *
 * <p>Starts the real application under {@code app.grpc.TomcatApp}'s own server — the exact one its
 * blocking {@code run(...)} builds, a grpc-servlet mounted on embedded Tomcat — on an ephemeral
 * port, confirms it came up, then stops it. That proves the application bootstraps and the
 * grpc-servlet transport assembles against it; exercising a call needs generated stubs the
 * application does not ship.
 */
@Timeout(30)
final class TomcatGrpcAppTest {

    @Test
    void bootsTheGrpcServerOverTomcat() throws Exception {
        assumeTrue(
                RuntimeServerFixture.isAvailable("io.grpc.servlet.jakarta.ServletServerBuilder"),
                "The grpc-servlet transport is not on the classpath.");

        Tomcat tomcat = app.grpc.TomcatApp.server(RuntimeServerFixture.grpcConfigOnPort(0));

        try {
            assertNotNull(tomcat.getServer());
        } finally {
            RuntimeServerFixture.stopQuietly(
                    () -> {
                        tomcat.stop();
                        tomcat.destroy();
                    });
        }
    }
}
