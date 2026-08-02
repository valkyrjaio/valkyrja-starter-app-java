/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.functional.entry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import app.tests.fixtures.entry.RuntimeServerFixture;
import io.grpc.Server;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * End-to-end test for the Netty gRPC runtime.
 *
 * <p>Starts the real application under {@code app.grpc.NettyApp}'s own server — the exact one its
 * blocking {@code run(...)} builds — on an ephemeral port, confirms it came up, then shuts it down.
 * That proves the application bootstraps and the Netty gRPC transport assembles against it;
 * exercising a call needs generated stubs the application does not ship.
 */
@Timeout(30)
final class NettyGrpcAppTest {

    @Test
    void bootsTheGrpcServerOverNetty() throws Exception {
        assumeTrue(
                RuntimeServerFixture.isAvailable(
                        "io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder"),
                "The Netty gRPC transport is not on the classpath.");

        Server server = app.grpc.NettyApp.server(RuntimeServerFixture.grpcConfigOnPort(0));

        try {
            assertFalse(server.isShutdown());
        } finally {
            RuntimeServerFixture.stopQuietly(server::shutdownNow);
        }
    }
}
