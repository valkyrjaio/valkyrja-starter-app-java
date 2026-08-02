/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.grpc.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.grpc.data.AppGrpcRoutingData;
import org.junit.jupiter.api.Test;

/** Test the sindri-generated {@link AppGrpcRoutingData}. */
final class AppGrpcRoutingDataTest {

    @Test
    void generatesRoutingData() {
        var data = new AppGrpcRoutingData();

        var routes = data.routes();
        assertEquals(1, routes.size());
        assertTrue(routes.containsKey("/app.Greeter/SayHello"));
        routes.values().forEach(route -> assertNotNull(route.get()));
    }
}
