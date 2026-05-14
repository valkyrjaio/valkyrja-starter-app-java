/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.cli.data;

import io.valkyrja.cli.routing.data.contract.CliRoutingDataContract;
import io.valkyrja.cli.routing.data.contract.RouteContract;

import java.util.Map;
import java.util.function.Supplier;

public record AppCliRoutingData() implements CliRoutingDataContract {

    @Override
    public Map<String, Supplier<RouteContract>> routes() {
        return Map.of();
    }
}
