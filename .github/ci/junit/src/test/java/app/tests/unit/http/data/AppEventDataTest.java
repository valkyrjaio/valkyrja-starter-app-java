/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.http.data.AppEventData;
import org.junit.jupiter.api.Test;

/** Test the {@link AppEventData}. */
final class AppEventDataTest {

    @Test
    void exposesEmptyEventData() {
        var data = new AppEventData();

        assertTrue(data.events().isEmpty());
        assertTrue(data.listeners().isEmpty());
    }
}
