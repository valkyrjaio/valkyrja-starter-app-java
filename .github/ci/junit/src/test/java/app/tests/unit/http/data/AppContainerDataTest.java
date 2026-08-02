/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.http.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.http.data.AppContainerData;
import org.junit.jupiter.api.Test;

/** Test the {@link AppContainerData}. */
final class AppContainerDataTest {

    @Test
    void exposesEmptyContainerData() {
        var data = new AppContainerData();

        assertTrue(data.aliases().isEmpty());
        assertTrue(data.callbacks().isEmpty());
        assertTrue(data.services().isEmpty());
        assertTrue(data.singletons().isEmpty());
    }
}
