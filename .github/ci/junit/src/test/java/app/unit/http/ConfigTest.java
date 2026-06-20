/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.unit.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import app.http.provider.ComponentProvider;
import io.valkyrja.application.data.contract.HttpConfigContract;
import app.http.Config;
import org.junit.jupiter.api.Test;

/** Test the {@link Config}. */
final class ConfigTest {

    @Test
    void isAnHttpConfigWithConfiguredValues() {
        var config = new Config();

        assertInstanceOf(HttpConfigContract.class, config);
        assertEquals("App", config.namespace());
        assertEquals("1.0.0", config.version());
        assertEquals("production", config.environment());
        assertFalse(config.debugMode());
        assertEquals("app/http/data", config.dataPath());
        assertEquals("app.http.data", config.dataNamespace());
    }

    @Test
    void registersComponentProvider() {
        assertInstanceOf(ComponentProvider.class, new Config().providers().get(0));
    }
}
