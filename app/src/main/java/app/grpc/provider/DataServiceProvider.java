/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.grpc.provider;

import app.grpc.data.AppContainerData;
import app.grpc.data.AppEventData;
import app.grpc.data.AppGrpcRoutingData;
import io.valkyrja.container.data.contract.ContainerDataContract;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.container.provider.contract.ServiceProviderContract;
import io.valkyrja.event.data.contract.EventDataContract;
import io.valkyrja.grpc.routing.data.contract.GrpcRoutingDataContract;
import java.util.Map;
import java.util.function.Consumer;

public final class DataServiceProvider implements ServiceProviderContract {

    @Override
    public Map<Class<?>, Consumer<ContainerContract>> publishers() {
        return Map.of(
                ContainerDataContract.class, DataServiceProvider::publishContainerData,
                EventDataContract.class, DataServiceProvider::publishEventData,
                GrpcRoutingDataContract.class, DataServiceProvider::publishGrpcRoutingData);
    }

    public static void publishContainerData(ContainerContract container) {
        container.setSingleton(ContainerDataContract.class, new AppContainerData());
    }

    public static void publishEventData(ContainerContract container) {
        container.setSingleton(EventDataContract.class, new AppEventData());
    }

    public static void publishGrpcRoutingData(ContainerContract container) {
        container.setSingleton(GrpcRoutingDataContract.class, new AppGrpcRoutingData());
    }
}
