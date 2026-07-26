/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.unit.grpc.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.grpc.data.AppGrpcRoutingData;
import org.junit.jupiter.api.Test;

/** Test the {@link AppGrpcRoutingData}. */
final class AppGrpcRoutingDataTest {

    @Test
    void exposesEmptyRoutes() {
        assertTrue(new AppGrpcRoutingData().routes().isEmpty());
    }
}
