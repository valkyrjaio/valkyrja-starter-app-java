/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.grpc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import app.grpc.controller.GreeterController;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.grpc.message.response.contract.ServiceResponseContract;
import io.valkyrja.grpc.routing.data.contract.RouteContract;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/** Test the {@link GreeterController}. */
final class GreeterControllerTest {

    @Test
    void sayHelloRepliesWithAnOkGreeting() {
        ServiceResponseContract response =
                new GreeterController()
                        .sayHello(mock(ContainerContract.class), mock(RouteContract.class));

        assertTrue(response.getStatus().isOk());
        assertEquals(
                "Hello!",
                new String(
                        (byte[]) response.getMessages().iterator().next(), StandardCharsets.UTF_8));
    }
}
