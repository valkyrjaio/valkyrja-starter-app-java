/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.grpc.controller;

import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.grpc.message.response.ServiceResponse;
import io.valkyrja.grpc.message.response.contract.ServiceResponseContract;
import io.valkyrja.grpc.routing.attribute.GrpcMethod;
import io.valkyrja.grpc.routing.attribute.GrpcService;
import io.valkyrja.grpc.routing.data.contract.RouteContract;
import java.nio.charset.StandardCharsets;

@GrpcService(service = "app.Greeter")
public class GreeterController {

    @GrpcMethod(name = "SayHello")
    public ServiceResponseContract sayHello(ContainerContract container, RouteContract route) {
        return ServiceResponse.ok("Hello!".getBytes(StandardCharsets.UTF_8));
    }
}
