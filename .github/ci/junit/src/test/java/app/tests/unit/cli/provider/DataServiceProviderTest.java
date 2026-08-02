/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.cli.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import app.cli.provider.DataServiceProvider;
import io.valkyrja.cli.routing.data.contract.CliRoutingDataContract;
import io.valkyrja.container.data.contract.ContainerDataContract;
import io.valkyrja.container.manager.Container;
import io.valkyrja.event.data.contract.EventDataContract;
import io.valkyrja.http.routing.data.contract.HttpRoutingDataContract;
import org.junit.jupiter.api.Test;

/** Test the {@link DataServiceProvider}. */
final class DataServiceProviderTest {

    @Test
    void publishersExposesAllData() {
        assertEquals(4, new DataServiceProvider().publishers().size());
    }

    @Test
    void publishMethodsBindData() {
        var container = new Container();

        DataServiceProvider.publishContainerData(container);
        DataServiceProvider.publishEventData(container);
        DataServiceProvider.publishCliRoutingData(container);
        DataServiceProvider.publishHttpRoutingData(container);

        assertNotNull(container.getSingleton(ContainerDataContract.class));
        assertNotNull(container.getSingleton(EventDataContract.class));
        assertNotNull(container.getSingleton(CliRoutingDataContract.class));
        assertNotNull(container.getSingleton(HttpRoutingDataContract.class));
    }
}
