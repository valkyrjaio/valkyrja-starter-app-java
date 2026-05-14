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
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.http.message.enum_.StatusCode;
import io.valkyrja.http.message.header.collection.HeaderCollection;
import io.valkyrja.http.message.response.contract.ResponseContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import io.valkyrja.http.routing.data.contract.RouteContract;
import io.valkyrja.http.routing.provider.contract.HttpRouteProviderContract;

import java.util.List;

public final class RouteProvider implements HttpRouteProviderContract {

    public static ResponseContract versionHandler(ContainerContract container, RouteContract route) {
        return HomeController.version(
                container.getSingleton(ApplicationContract.class),
                container.getSingleton(ResponseFactoryContract.class));
    }

    public static ResponseContract textHandler(ContainerContract container, RouteContract route) {
        return HomeController.text();
    }

    public static ResponseContract welcomeHandler(ContainerContract container, RouteContract route) {
        return container.getSingleton(HomeController.class).welcome();
    }

    public static ResponseContract welcomeCachedHandler(ContainerContract container, RouteContract route) {
        return container.getSingleton(HomeController.class).welcomeCached();
    }

    public static ResponseContract homeHandler(ContainerContract container, RouteContract route) {
        return container.getSingleton(HomeController.class).home();
    }

    public static ResponseContract jsonHandler(ContainerContract container, RouteContract route) {
        return container.getSingleton(HomeController.class).json();
    }

    @Override
    public List<Class<?>> getControllerClasses() {
        return List.of(HomeController.class);
    }

    @Override
    public List<RouteContract> getRoutes() {
        return List.of();
    }
}
