/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.http.controller.HomeController;
import app.http.data.AppContainerData;
import org.junit.jupiter.api.Test;

/** Test the sindri-generated {@link AppContainerData}. */
final class AppContainerDataTest {

    @Test
    void generatesContainerData() {
        var data = new AppContainerData();

        assertTrue(data.aliases().isEmpty());
        assertTrue(data.services().isEmpty());
        assertTrue(data.singletons().isEmpty());

        var callbacks = data.callbacks();
        assertFalse(callbacks.isEmpty());
        assertTrue(callbacks.containsKey(HomeController.class));
    }
}
