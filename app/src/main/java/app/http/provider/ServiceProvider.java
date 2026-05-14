/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http.provider;

import app.http.controller.HomeController;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.container.provider.contract.ServiceProviderContract;
import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;

import java.util.Map;
import java.util.function.Consumer;

public final class ServiceProvider implements ServiceProviderContract {

    @Override
    public Map<Class<?>, Consumer<ContainerContract>> publishers() {
        return Map.of(HomeController.class, ServiceProvider::publishHomeController);
    }

    public static void publishHomeController(ContainerContract container) {
        container.setSingleton(
                HomeController.class,
                new HomeController(
                        container.getSingleton(ServerRequestContract.class),
                        container.getSingleton(ResponseFactoryContract.class)));
    }
}
