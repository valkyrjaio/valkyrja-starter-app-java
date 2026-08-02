/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.grpc.data;

import io.valkyrja.grpc.routing.data.contract.GrpcRoutingDataContract;
import io.valkyrja.grpc.routing.data.contract.RouteContract;

import java.util.Map;
import java.util.function.Supplier;

public record AppGrpcRoutingData() implements GrpcRoutingDataContract {

    @Override
    public Map<String, Supplier<RouteContract>> routes() {
        return Map.of();
    }
}
