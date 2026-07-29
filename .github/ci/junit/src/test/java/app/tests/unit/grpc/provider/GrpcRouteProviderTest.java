/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.grpc.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.grpc.controller.GreeterController;
import app.grpc.provider.GrpcRouteProvider;
import org.junit.jupiter.api.Test;

/** Test the {@link GrpcRouteProvider}. */
final class GrpcRouteProviderTest {

    private final GrpcRouteProvider provider = new GrpcRouteProvider();

    @Test
    void registersTheGreeterController() {
        assertEquals(java.util.List.of(GreeterController.class), provider.getControllerClasses());
    }

    @Test
    void hasNoManualRoutes() {
        assertTrue(provider.getRoutes().isEmpty());
    }
}
