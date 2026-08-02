/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.grpc.data;

import io.valkyrja.grpc.routing.data.contract.GrpcRoutingDataContract;
import io.valkyrja.grpc.routing.data.contract.RouteContract;

import java.util.Map;
import java.util.function.Supplier;

public record AppGrpcRoutingData() implements GrpcRoutingDataContract {

    @Override
    public Map<String, Supplier<RouteContract>> routes() {
        return Map.of();
    }
}
