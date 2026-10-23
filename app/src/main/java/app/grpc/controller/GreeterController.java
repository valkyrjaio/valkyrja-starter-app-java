/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.grpc.controller;

import app.grpc.provider.GrpcRouteProvider;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.grpc.message.response.ServiceResponse;
import io.valkyrja.grpc.message.response.contract.ServiceResponseContract;
import io.valkyrja.grpc.routing.attribute.Route;
import io.valkyrja.grpc.routing.attribute.Service;
import io.valkyrja.grpc.routing.attribute.route.RouteHandler;
import io.valkyrja.grpc.routing.data.contract.RouteContract;
import java.nio.charset.StandardCharsets;

@Service(service = "app.Greeter")
public class GreeterController {

    @Route(name = "SayHello")
    @RouteHandler(handlerClass = GrpcRouteProvider.class, handlerMethod = "sayHelloHandler")
    public ServiceResponseContract sayHello(ContainerContract container, RouteContract route) {
        return ServiceResponse.ok("Hello!".getBytes(StandardCharsets.UTF_8));
    }
}
