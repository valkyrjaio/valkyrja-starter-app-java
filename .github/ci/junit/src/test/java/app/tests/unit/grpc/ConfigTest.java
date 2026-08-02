/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.grpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import app.grpc.Config;
import app.grpc.provider.ComponentProvider;
import io.valkyrja.application.data.contract.GrpcConfigContract;
import org.junit.jupiter.api.Test;

/** Test the {@link Config}. */
final class ConfigTest {

    @Test
    void isAGrpcConfigWithConfiguredValues() {
        var config = new Config();

        assertInstanceOf(GrpcConfigContract.class, config);
        assertEquals("App", config.namespace());
        assertEquals("1.0.0", config.version());
        assertEquals("production", config.environment());
        assertFalse(config.debugMode());
        assertEquals("app/grpc/data", config.dataPath());
        assertEquals("app.grpc.data", config.dataNamespace());
        assertEquals(50051, config.port());
    }

    @Test
    void registersComponentProvider() {
        assertInstanceOf(ComponentProvider.class, new Config().providers().get(0));
    }
}
