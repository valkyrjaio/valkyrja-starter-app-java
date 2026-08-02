/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.cli.Config;
import app.cli.provider.ComponentProvider;
import io.valkyrja.application.data.contract.CliConfigContract;
import org.junit.jupiter.api.Test;

/** Test the {@link Config}. */
final class ConfigTest {

    @Test
    void isACliConfigWithConfiguredValues() {
        var config = new Config();

        assertInstanceOf(CliConfigContract.class, config);
        assertEquals("App", config.namespace());
        assertEquals("1.0.0", config.version());
        assertEquals("production", config.environment());
        assertTrue(config.debugMode());
        assertEquals("UTC", config.timezone());
        assertEquals("app/cli/data", config.dataPath());
        assertEquals("app.cli.data", config.dataNamespace());
    }

    @Test
    void registersComponentProvider() {
        assertInstanceOf(ComponentProvider.class, new Config().providers().get(0));
    }
}
