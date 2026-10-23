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
import static org.junit.jupiter.api.Assertions.assertSame;

import app.grpc.controller.GreeterController;
import app.grpc.provider.ServiceProvider;
import io.valkyrja.container.manager.Container;
import org.junit.jupiter.api.Test;

/** Test the {@link ServiceProvider}. */
final class ServiceProviderTest {

    private final ServiceProvider provider = new ServiceProvider();

    @Test
    void publishesTheGreeterController() {
        assertEquals(1, provider.publishers().size());
        assertNotNull(provider.publishers().get(GreeterController.class));
    }

    @Test
    void publishGreeterControllerBindsOneInstance() {
        var container = new Container();

        ServiceProvider.publishGreeterController(container);

        // The route handler resolves the controller from the container, so the binding is what
        // lets the route run at all.
        GreeterController controller = container.getSingleton(GreeterController.class);
        assertNotNull(controller);
        assertSame(controller, container.getSingleton(GreeterController.class));
    }
}
