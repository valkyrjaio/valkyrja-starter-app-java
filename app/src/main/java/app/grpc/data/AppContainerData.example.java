/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.grpc.data;

import io.valkyrja.container.data.contract.ContainerDataContract;
import io.valkyrja.container.manager.contract.ContainerContract;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public record AppContainerData() implements ContainerDataContract {

    @Override
    public Map<Class<?>, Class<?>> aliases() {
        return Map.of();
    }

    @Override
    public Map<Class<?>, Consumer<ContainerContract>> callbacks() {
        return Map.of();
    }

    @Override
    public Map<Class<?>, BiFunction<ContainerContract, Map<String, Object>, Object>> services() {
        return Map.of();
    }

    @Override
    public Map<Class<?>, Class<?>> singletons() {
        return Map.of();
    }
}
