/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.http.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import app.http.controller.HomeController;
import app.http.provider.HttpRouteProvider;
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.container.manager.Container;
import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import io.valkyrja.http.routing.data.contract.RouteContract;
import org.junit.jupiter.api.Test;

/** Test class. */
final class HttpRouteProviderTest {

    private Container container() {
        var container = new Container();
        container.setSingleton(ApplicationContract.class, mock(ApplicationContract.class));
        container.setSingleton(ResponseFactoryContract.class, mock(ResponseFactoryContract.class));
        container.setSingleton(
                HomeController.class,
                new HomeController(
                        mock(ServerRequestContract.class), mock(ResponseFactoryContract.class)));

        return container;
    }

    @Test
    void exposesControllerAndEmptyRoutes() {
        var provider = new HttpRouteProvider();

        assertEquals(1, provider.getControllerClasses().size());
        assertTrue(provider.getRoutes().isEmpty());
    }

    @Test
    void handlersDelegateToHomeController() {
        var container = container();
        var route = mock(RouteContract.class);

        HttpRouteProvider.versionHandler(container, route);
        assertNotNull(HttpRouteProvider.textHandler(container, route));
        assertNotNull(HttpRouteProvider.welcomeHandler(container, route));
        assertNotNull(HttpRouteProvider.welcomeCachedHandler(container, route));
        assertNotNull(HttpRouteProvider.homeHandler(container, route));
        HttpRouteProvider.jsonHandler(container, route);
    }
}
