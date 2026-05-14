/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.cli.data;

import io.valkyrja.http.routing.data.contract.HttpRoutingDataContract;
import io.valkyrja.http.routing.data.contract.RouteContract;

import java.util.Map;
import java.util.function.Supplier;

public record AppHttpRoutingData() implements HttpRoutingDataContract {

    @Override
    public Map<String, Supplier<RouteContract>> routes() {
        return Map.of();
    }

    @Override
    public Map<String, Map<String, String>> paths() {
        return Map.of();
    }

    @Override
    public Map<String, Map<String, String>> dynamicPaths() {
        return Map.of();
    }

    @Override
    public Map<String, Map<String, String>> regexes() {
        return Map.of();
    }
}
