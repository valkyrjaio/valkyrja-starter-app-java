/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.cli.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import app.cli.data.AppContainerData;
import org.junit.jupiter.api.Test;

/** Test the {@link AppContainerData}. */
final class AppContainerDataTest {

    @Test
    void exposesEmptyContainerData() {
        var data = new AppContainerData();

        assertTrue(data.aliases().isEmpty());
        assertTrue(data.callbacks().isEmpty());
        assertTrue(data.services().isEmpty());
        assertTrue(data.singletons().isEmpty());
    }
}
