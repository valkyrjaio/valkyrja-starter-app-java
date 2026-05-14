/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http.provider;

import app.http.data.AppContainerData;
import app.http.data.AppEventData;
import app.http.data.AppHttpRoutingData;
import io.valkyrja.container.data.contract.ContainerDataContract;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.container.provider.contract.ServiceProviderContract;
import io.valkyrja.event.data.contract.EventDataContract;
import io.valkyrja.http.routing.data.contract.HttpRoutingDataContract;

import java.util.Map;
import java.util.function.Consumer;

public final class DataServiceProvider implements ServiceProviderContract {

    @Override
    public Map<Class<?>, Consumer<ContainerContract>> publishers() {
        return Map.of(
                ContainerDataContract.class,  DataServiceProvider::publishContainerData,
                EventDataContract.class,      DataServiceProvider::publishEventData,
                HttpRoutingDataContract.class, DataServiceProvider::publishHttpRoutingData);
    }

    public static void publishContainerData(ContainerContract container) {
        container.setSingleton(ContainerDataContract.class, new AppContainerData());
    }

    public static void publishEventData(ContainerContract container) {
        container.setSingleton(EventDataContract.class, new AppEventData());
    }

    public static void publishHttpRoutingData(ContainerContract container) {
        container.setSingleton(HttpRoutingDataContract.class, new AppHttpRoutingData());
    }
}
