/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.grpc.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import app.grpc.provider.DataServiceProvider;
import io.valkyrja.container.data.contract.ContainerDataContract;
import io.valkyrja.container.manager.Container;
import io.valkyrja.event.data.contract.EventDataContract;
import io.valkyrja.grpc.routing.data.contract.GrpcRoutingDataContract;
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
        DataServiceProvider.publishGrpcRoutingData(container);

        assertNotNull(container.getSingleton(ContainerDataContract.class));
        assertNotNull(container.getSingleton(EventDataContract.class));
        assertNotNull(container.getSingleton(GrpcRoutingDataContract.class));
    }
}
