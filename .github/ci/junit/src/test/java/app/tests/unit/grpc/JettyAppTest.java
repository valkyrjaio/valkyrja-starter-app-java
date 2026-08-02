/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.grpc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Test the {@link app.grpc.JettyApp} entry point.
 *
 * <p>The entry's {@code main} blocks on the Jetty gRPC server loop, so it cannot run in-process;
 * the server it builds is covered end to end by {@code
 * app.tests.functional.entry.JettyGrpcAppTest}.
 */
final class JettyAppTest {

    @Test
    void isInstantiable() {
        assertNotNull(new app.grpc.JettyApp());
    }
}
