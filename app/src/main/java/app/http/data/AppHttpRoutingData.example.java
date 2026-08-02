/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.http.data;

import io.valkyrja.http.routing.data.contract.HttpRoutingDataContract;
import io.valkyrja.http.routing.data.contract.RouteContract;

import java.util.Map;
import java.util.function.Supplier;

public record AppHttpRoutingData() implements HttpRoutingDataContract {

    @Override
    public Map<String, Supplier<RouteContract>> routes() {
        return Map.of();
    }

    @Override
    public Map<String, Map<String, String>> paths() {
        return Map.of();
    }

    @Override
    public Map<String, Map<String, String>> dynamicPaths() {
        return Map.of();
    }

    @Override
    public Map<String, Map<String, String>> regexes() {
        return Map.of();
    }
}
