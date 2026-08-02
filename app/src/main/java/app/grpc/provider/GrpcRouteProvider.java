/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.grpc.provider;

import app.grpc.controller.GreeterController;
import io.valkyrja.grpc.routing.data.contract.RouteContract;
import io.valkyrja.grpc.routing.provider.contract.GrpcRouteProviderContract;
import java.util.List;

public final class GrpcRouteProvider implements GrpcRouteProviderContract {

    @Override
    public List<Class<?>> getControllerClasses() {
        return List.of(GreeterController.class);
    }

    @Override
    public List<RouteContract> getRoutes() {
        return List.of();
    }
}
