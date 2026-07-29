/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
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
