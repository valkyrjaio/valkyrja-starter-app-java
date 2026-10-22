/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.grpc.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import app.grpc.controller.GreeterController;
import app.grpc.provider.GrpcRouteProvider;
import io.valkyrja.container.manager.Container;
import io.valkyrja.container.throwable.exception.ContainerInvalidReferenceException;
import io.valkyrja.grpc.message.response.contract.ServiceResponseContract;
import io.valkyrja.grpc.routing.data.contract.RouteContract;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/** Test the {@link GrpcRouteProvider}. */
final class GrpcRouteProviderTest {

    private final GrpcRouteProvider provider = new GrpcRouteProvider();

    @Test
    void registersTheGreeterController() {
        assertEquals(java.util.List.of(GreeterController.class), provider.getControllerClasses());
    }

    @Test
    void hasNoManualRoutes() {
        assertTrue(provider.getRoutes().isEmpty());
    }

    @Test
    void sayHelloHandlerReadsTheControllerFromTheContainer() {
        // A handler that built the controller itself would answer the same, so the empty
        // container is what pins the resolution.
        assertThrows(
                ContainerInvalidReferenceException.class,
                () ->
                        GrpcRouteProvider.sayHelloHandler(
                                new Container(), mock(RouteContract.class)));
    }

    @Test
    void sayHelloHandlerAnswersThroughTheControllerTheContainerHolds() {
        var container = new Container();
        container.setSingleton(GreeterController.class, new GreeterController());

        ServiceResponseContract response =
                GrpcRouteProvider.sayHelloHandler(container, mock(RouteContract.class));

        assertTrue(response.getStatus().isOk());
        assertEquals(
                "Hello!",
                new String(
                        (byte[]) response.getMessages().iterator().next(), StandardCharsets.UTF_8));
    }
}
