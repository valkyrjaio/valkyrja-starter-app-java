/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import app.http.provider.ComponentProvider;
import app.http.provider.DataServiceProvider;
import app.http.provider.HttpRouteProvider;
import app.http.provider.ServiceProvider;
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.application.provider.HttpApplicationComponentProvider;
import io.valkyrja.container.data.contract.ContainerDataContract;
import io.valkyrja.container.manager.Container;
import org.junit.jupiter.api.Test;

/** Test the {@link ComponentProvider}. */
final class ComponentProviderTest {

    private final ComponentProvider provider = new ComponentProvider();
    private final ApplicationContract app = mock(ApplicationContract.class);

    @Test
    void getComponentProvidersReturnsTheComponentProviders() {
        var providers = provider.getComponentProviders(app);

        assertEquals(1, providers.size());
        assertInstanceOf(HttpApplicationComponentProvider.class, providers.get(0));
    }

    @Test
    void getContainerProvidersReturnsTheServiceProviders() {
        var providers = provider.getContainerProviders(app);

        assertEquals(2, providers.size());
        assertInstanceOf(DataServiceProvider.class, providers.get(0));
        assertInstanceOf(ServiceProvider.class, providers.get(1));
    }

    @Test
    void getEventProvidersIsEmpty() {
        assertTrue(provider.getEventProviders(app).isEmpty());
    }

    @Test
    void getCliProvidersIsEmpty() {
        assertTrue(provider.getCliProviders(app).isEmpty());
    }

    @Test
    void getHttpProvidersReturnsTheHttpRouteProviders() {
        var providers = provider.getHttpProviders(app);

        assertEquals(1, providers.size());
        assertInstanceOf(HttpRouteProvider.class, providers.get(0));
    }

    @Test
    void getGrpcProvidersIsEmpty() {
        assertTrue(provider.getGrpcProviders(app).isEmpty());
    }

    @Test
    void publishSkipsInDebugMode() {
        when(app.getDebugMode()).thenReturn(true);

        ComponentProvider.publish(app);
    }

    @Test
    void publishBindsContainerDataWhenNotDebug() {
        var container = new Container();
        when(app.getDebugMode()).thenReturn(false);
        when(app.getContainer()).thenReturn(container);

        ComponentProvider.publish(app);

        assertNotNull(container.getSingleton(ContainerDataContract.class));
    }
}
