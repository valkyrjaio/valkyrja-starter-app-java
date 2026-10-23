/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.grpc.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.grpc.controller.GreeterController;
import app.grpc.data.AppContainerData;
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
        // The route handler resolves the controller from the container, and the generated data is
        // the only thing that binds it when the cache is on, which is the default the app ships.
        assertTrue(callbacks.containsKey(GreeterController.class));
    }
}
