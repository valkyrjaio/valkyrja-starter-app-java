/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http.controller.abstract_;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import app.http.controller.abstract_.Controller;
import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import org.junit.jupiter.api.Test;

/** Test class. */
final class ControllerTest {

    @Test
    void subclassConstructsThroughBase() {
        var controller =
                new Controller(
                        mock(ServerRequestContract.class), mock(ResponseFactoryContract.class)) {};

        assertNotNull(controller);
    }
}
