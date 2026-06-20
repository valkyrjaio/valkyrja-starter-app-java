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
import app.http.data.AppEventData;
import org.junit.jupiter.api.Test;

/** Test the {@link AppEventData}. */
final class AppEventDataTest {

    @Test
    void exposesEmptyEventData() {
        var data = new AppEventData();

        assertTrue(data.events().isEmpty());
        assertTrue(data.listeners().isEmpty());
    }
}
