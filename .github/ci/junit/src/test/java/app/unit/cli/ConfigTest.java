/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.unit.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.cli.provider.ComponentProvider;
import io.valkyrja.application.data.contract.CliConfigContract;
import app.cli.Config;
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
