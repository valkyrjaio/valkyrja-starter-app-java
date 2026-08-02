/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.cli.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.cli.data.AppCliRoutingData;
import org.junit.jupiter.api.Test;

/** Test the {@link AppCliRoutingData}. */
final class AppCliRoutingDataTest {

    @Test
    void exposesEmptyRoutes() {
        assertTrue(new AppCliRoutingData().routes().isEmpty());
    }
}
