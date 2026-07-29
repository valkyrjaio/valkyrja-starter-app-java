/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
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
