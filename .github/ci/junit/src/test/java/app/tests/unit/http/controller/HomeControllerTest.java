/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import app.http.controller.HomeController;
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import org.junit.jupiter.api.Test;

/** Test class. */
final class HomeControllerTest {

    private HomeController controller() {
        return new HomeController(
                mock(ServerRequestContract.class), mock(ResponseFactoryContract.class));
    }

    @Test
    void staticHandlersProduceResponses() {
        HomeController.version(
                mock(ApplicationContract.class), mock(ResponseFactoryContract.class));
        assertNotNull(HomeController.text());
    }

    @Test
    void instanceHandlersProduceResponses() {
        var controller = controller();

        assertNotNull(controller.welcome());
        assertNotNull(controller.welcomeCached());
        assertNotNull(controller.home());
        controller.json();
    }
}
