/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.grpc.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.grpc.data.AppGrpcRoutingData;
import org.junit.jupiter.api.Test;

/** Test the {@link AppGrpcRoutingData}. */
final class AppGrpcRoutingDataTest {

    @Test
    void exposesEmptyRoutes() {
        assertTrue(new AppGrpcRoutingData().routes().isEmpty());
    }
}
