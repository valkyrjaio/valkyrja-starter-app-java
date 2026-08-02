/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.cli.controller.abstract_;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import app.cli.controller.abstract_.Controller;
import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.output.factory.contract.OutputFactoryContract;
import org.junit.jupiter.api.Test;

/** Test class. */
final class ControllerTest {

    @Test
    void subclassConstructsThroughBase() {
        var controller =
                new Controller(mock(InputContract.class), mock(OutputFactoryContract.class)) {};

        assertNotNull(controller);
    }
}
