/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.cli.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.cli.data.AppHttpRoutingData;
import org.junit.jupiter.api.Test;

/** Test the sindri-generated {@link AppHttpRoutingData}. */
final class AppHttpRoutingDataTest {

    @Test
    void generatesRoutingData() {
        var data = new AppHttpRoutingData();

        var routes = data.routes();
        // Eight routes on HomeController plus the eighteen routing permutations.
        assertEquals(26, routes.size());
        assertTrue(routes.containsKey("welcome"));
        routes.values().forEach(route -> assertNotNull(route.get()));

        var paths = data.paths();
        assertTrue(paths.containsKey("GET"));
        assertTrue(paths.containsKey("POST"));
        assertTrue(paths.containsKey("PUT"));
        assertTrue(paths.containsKey("HEAD"));

        // The routing permutations contribute dynamic paths and their regexes.
        assertFalse(data.dynamicPaths().isEmpty());
        assertFalse(data.regexes().isEmpty());
    }
}
