/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.unit.http.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import app.http.controller.HomeController;
import app.http.data.AppContainerData;
import org.junit.jupiter.api.Test;

/** Test the sindri-generated {@link AppContainerData}. */
final class AppContainerDataTest {

    @Test
    void generatesContainerData() {
        var data = new AppContainerData();

        assertTrue(data.aliases().isEmpty());
        assertTrue(data.services().isEmpty());
        assertTrue(data.singletons().isEmpty());

        var callbacks = data.callbacks();
        assertFalse(callbacks.isEmpty());
        assertTrue(callbacks.containsKey(HomeController.class));
    }
}
