/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.http.data.AppHttpRoutingData;
import org.junit.jupiter.api.Test;

/** Test the {@link AppHttpRoutingData}. */
final class AppHttpRoutingDataTest {

    @Test
    void exposesEmptyRoutingData() {
        var data = new AppHttpRoutingData();

        assertTrue(data.routes().isEmpty());
        assertTrue(data.paths().isEmpty());
        assertTrue(data.dynamicPaths().isEmpty());
        assertTrue(data.regexes().isEmpty());
    }
}
