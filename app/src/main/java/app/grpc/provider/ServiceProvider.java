/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.grpc.provider;

import app.grpc.controller.GreeterController;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.container.provider.contract.ServiceProviderContract;
import java.util.Map;
import java.util.function.Consumer;

public final class ServiceProvider implements ServiceProviderContract {

    @Override
    public Map<Class<?>, Consumer<ContainerContract>> publishers() {
        return Map.of(GreeterController.class, ServiceProvider::publishGreeterController);
    }

    public static void publishGreeterController(ContainerContract container) {
        container.setSingleton(GreeterController.class, new GreeterController());
    }
}
