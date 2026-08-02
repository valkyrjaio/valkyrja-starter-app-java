/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.cli.data;

import io.valkyrja.cli.routing.data.contract.CliRoutingDataContract;
import io.valkyrja.cli.routing.data.contract.RouteContract;

import java.util.Map;
import java.util.function.Supplier;

public record AppCliRoutingData() implements CliRoutingDataContract {

    @Override
    public Map<String, Supplier<RouteContract>> routes() {
        return Map.of();
    }
}
