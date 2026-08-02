/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.http.data.AppCliRoutingData;
import org.junit.jupiter.api.Test;

/** Test the sindri-generated {@link AppCliRoutingData} — an HTTP app exposes no CLI routes. */
final class AppCliRoutingDataTest {

    @Test
    void generatesEmptyCliRoutes() {
        assertTrue(new AppCliRoutingData().routes().isEmpty());
    }
}
