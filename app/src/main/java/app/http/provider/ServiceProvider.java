/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.http.provider;

import app.http.controller.HomeController;
import app.http.controller.RoutingPermutationsController;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.container.provider.contract.ServiceProviderContract;
import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import java.util.Map;
import java.util.function.Consumer;

public final class ServiceProvider implements ServiceProviderContract {

    @Override
    public Map<Class<?>, Consumer<ContainerContract>> publishers() {
        return Map.of(
                HomeController.class,
                ServiceProvider::publishHomeController,
                RoutingPermutationsController.class,
                ServiceProvider::publishRoutingPermutationsController);
    }

    public static void publishHomeController(ContainerContract container) {
        container.setSingleton(
                HomeController.class,
                new HomeController(
                        container.getSingleton(ServerRequestContract.class),
                        container.getSingleton(ResponseFactoryContract.class)));
    }

    public static void publishRoutingPermutationsController(ContainerContract container) {
        container.setSingleton(
                RoutingPermutationsController.class,
                new RoutingPermutationsController(
                        container.getSingleton(ServerRequestContract.class),
                        container.getSingleton(ResponseFactoryContract.class)));
    }
}
