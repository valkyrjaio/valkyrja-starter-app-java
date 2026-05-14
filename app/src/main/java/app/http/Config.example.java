/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http;

import app.http.provider.ComponentProvider;
import io.valkyrja.application.data.contract.HttpConfigContract;
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.application.provider.contract.ComponentProviderContract;
import io.valkyrja.http.middleware.contract.RequestReceivedMiddlewareContract;
import io.valkyrja.http.middleware.contract.RouteDispatchedMiddlewareContract;
import io.valkyrja.http.middleware.contract.RouteMatchedMiddlewareContract;
import io.valkyrja.http.middleware.contract.RouteNotMatchedMiddlewareContract;
import io.valkyrja.http.middleware.contract.SendingResponseMiddlewareContract;
import io.valkyrja.http.middleware.contract.TerminatedMiddlewareContract;
import io.valkyrja.http.middleware.contract.ThrowableCaughtMiddlewareContract;

import java.util.List;
import java.util.function.Consumer;

public record Config(
    String namespace,
    String dir,
    String version,
    String environment,
    boolean debugMode,
    String timezone,
    String key,
    String dataPath,
    String dataNamespace,
    Integer port,
    List<Class<? extends RequestReceivedMiddlewareContract>> requestReceivedMiddleware,
    List<Class<? extends RouteMatchedMiddlewareContract>> routeMatchedMiddleware,
    List<Class<? extends RouteNotMatchedMiddlewareContract>> routeNotMatchedMiddleware,
    List<Class<? extends RouteDispatchedMiddlewareContract>> routeDispatchedMiddleware,
    List<Class<? extends ThrowableCaughtMiddlewareContract>> throwableCaughtMiddleware,
    List<Class<? extends SendingResponseMiddlewareContract>> sendingResponseMiddleware,
    List<Class<? extends TerminatedMiddlewareContract>> terminatedMiddleware,
    List<ComponentProviderContract> providers,
    List<Consumer<ApplicationContract>> callbacks
) implements HttpConfigContract {

    public Config() {
        this(
            "App",
            System.getProperty("user.dir"),
            "1.0.0",
            "production",
            false,
            "UTC",
            "some_secret_app_key",
            "app/http/provider/data",
            "app.http.provider.data",
            8080,
            List.of(),
            List.of(),
            List.of(),
            List.of(),
            List.of(),
            List.of(),
            List.of(),
            List.of(new ComponentProvider()),
            List.of(ComponentProvider::publish)
        );
    }
}
