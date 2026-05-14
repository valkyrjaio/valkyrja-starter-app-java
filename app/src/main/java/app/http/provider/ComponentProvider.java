/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http.provider;

import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.application.provider.HttpApplicationComponentProvider;
import io.valkyrja.application.provider.contract.ComponentProviderContract;
import io.valkyrja.cli.routing.provider.contract.CliRouteProviderContract;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.container.provider.contract.ServiceProviderContract;
import io.valkyrja.event.provider.contract.ListenerProviderContract;
import io.valkyrja.http.routing.provider.contract.HttpRouteProviderContract;

import java.util.List;

public final class ComponentProvider implements ComponentProviderContract {

    public static void publish(ApplicationContract app) {
        if (app.getDebugMode()) {
            return;
        }

        ContainerContract container = app.getContainer();

        DataServiceProvider.publishContainerData(container);
    }

    @Override
    public List<ComponentProviderContract> getComponentProviders(ApplicationContract app) {
        return List.of(new HttpApplicationComponentProvider());
    }

    @Override
    public List<ServiceProviderContract> getContainerProviders(ApplicationContract app) {
        return List.of(
                new DataServiceProvider(),
                new ServiceProvider());
    }

    @Override
    public List<ListenerProviderContract> getEventProviders(ApplicationContract app) {
        return List.of();
    }

    @Override
    public List<CliRouteProviderContract> getCliProviders(ApplicationContract app) {
        return List.of();
    }

    @Override
    public List<HttpRouteProviderContract> getHttpProviders(ApplicationContract app) {
        return List.of(new RouteProvider());
    }
}
