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
import static org.mockito.Mockito.mock;

import app.http.controller.HomeController;
import app.http.provider.ServiceProvider;
import io.valkyrja.container.manager.Container;
import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import org.junit.jupiter.api.Test;

/** Test class. */
final class ServiceProviderTest {

    @Test
    void publishersExposesHomeController() {
        assertEquals(2, new ServiceProvider().publishers().size());
    }

    @Test
    void publishHomeControllerBindsController() {
        var container = new Container();
        container.setSingleton(ServerRequestContract.class, mock(ServerRequestContract.class));
        container.setSingleton(ResponseFactoryContract.class, mock(ResponseFactoryContract.class));

        ServiceProvider.publishHomeController(container);

        assertNotNull(container.getSingleton(HomeController.class));
    }
}
