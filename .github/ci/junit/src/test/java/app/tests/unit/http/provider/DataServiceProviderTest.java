/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.http.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import app.http.provider.DataServiceProvider;
import io.valkyrja.container.data.contract.ContainerDataContract;
import io.valkyrja.container.manager.Container;
import io.valkyrja.event.data.contract.EventDataContract;
import io.valkyrja.http.routing.data.contract.HttpRoutingDataContract;
import org.junit.jupiter.api.Test;

/** Test the {@link DataServiceProvider}. */
final class DataServiceProviderTest {

    @Test
    void publishersExposesAllData() {
        assertEquals(3, new DataServiceProvider().publishers().size());
    }

    @Test
    void publishMethodsBindData() {
        var container = new Container();

        DataServiceProvider.publishContainerData(container);
        DataServiceProvider.publishEventData(container);
        DataServiceProvider.publishHttpRoutingData(container);

        assertNotNull(container.getSingleton(ContainerDataContract.class));
        assertNotNull(container.getSingleton(EventDataContract.class));
        assertNotNull(container.getSingleton(HttpRoutingDataContract.class));
    }
}
