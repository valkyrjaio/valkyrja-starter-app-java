/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.cli.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.cli.data.AppCliRoutingData;
import org.junit.jupiter.api.Test;

/** Test the sindri-generated {@link AppCliRoutingData}. */
final class AppCliRoutingDataTest {

    @Test
    void generatesTestCommandRoute() {
        var routes = new AppCliRoutingData().routes();

        assertEquals(1, routes.size());
        assertTrue(routes.containsKey("test"));
        routes.values().forEach(route -> assertNotNull(route.get()));
    }
}
