/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.grpc.provider;

import app.grpc.controller.GreeterController;
import io.valkyrja.grpc.routing.data.contract.RouteContract;
import io.valkyrja.grpc.routing.provider.contract.GrpcRouteProviderContract;
import java.util.List;

public final class GrpcRouteProvider implements GrpcRouteProviderContract {

    @Override
    public List<Class<?>> getControllerClasses() {
        return List.of(GreeterController.class);
    }

    @Override
    public List<RouteContract> getRoutes() {
        return List.of();
    }
}
