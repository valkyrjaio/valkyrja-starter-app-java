/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.cli.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.cli.data.AppHttpRoutingData;
import org.junit.jupiter.api.Test;

/** Test the {@link AppHttpRoutingData}. */
final class AppHttpRoutingDataTest {

    @Test
    void exposesEmptyRoutingData() {
        var data = new AppHttpRoutingData();

        assertTrue(data.routes().isEmpty());
        assertTrue(data.paths().isEmpty());
        assertTrue(data.dynamicPaths().isEmpty());
        assertTrue(data.regexes().isEmpty());
    }
}
