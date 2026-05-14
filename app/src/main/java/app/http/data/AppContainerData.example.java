/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http.data;

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
