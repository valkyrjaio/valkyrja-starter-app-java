/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.grpc.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.grpc.data.AppGrpcRoutingData;
import org.junit.jupiter.api.Test;

/** Test the sindri-generated {@link AppGrpcRoutingData}. */
final class AppGrpcRoutingDataTest {

    @Test
    void generatesRoutingData() {
        var data = new AppGrpcRoutingData();

        var routes = data.routes();
        assertEquals(1, routes.size());
        assertTrue(routes.containsKey("/app.Greeter/SayHello"));
        routes.values().forEach(route -> assertNotNull(route.get()));
    }
}
