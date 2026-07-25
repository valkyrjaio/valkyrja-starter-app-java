/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.unit.http.data;

import static org.junit.jupiter.api.Assertions.assertTrue;
import app.http.data.AppCliRoutingData;
import org.junit.jupiter.api.Test;

/** Test the sindri-generated {@link AppCliRoutingData} — an HTTP app exposes no CLI routes. */
final class AppCliRoutingDataTest {

    @Test
    void generatesEmptyCliRoutes() {
        assertTrue(new AppCliRoutingData().routes().isEmpty());
    }
}
