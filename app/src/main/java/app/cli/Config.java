/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.cli;

import app.cli.provider.ComponentProvider;
import io.valkyrja.application.data.contract.CliConfigContract;
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.application.provider.contract.ComponentProviderContract;
import io.valkyrja.cli.middleware.contract.InputReceivedMiddlewareContract;
import io.valkyrja.cli.middleware.contract.ProcessExitingMiddlewareContract;
import io.valkyrja.cli.middleware.contract.RouteDispatchedMiddlewareContract;
import io.valkyrja.cli.middleware.contract.RouteMatchedMiddlewareContract;
import io.valkyrja.cli.middleware.contract.RouteNotMatchedMiddlewareContract;
import io.valkyrja.cli.middleware.contract.ThrowableCaughtMiddlewareContract;
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
        String applicationName,
        String defaultCommandName,
        List<Class<? extends InputReceivedMiddlewareContract>> inputReceivedMiddleware,
        List<Class<? extends RouteMatchedMiddlewareContract>> routeMatchedMiddleware,
        List<Class<? extends RouteNotMatchedMiddlewareContract>> routeNotMatchedMiddleware,
        List<Class<? extends RouteDispatchedMiddlewareContract>> routeDispatchedMiddleware,
        List<Class<? extends ThrowableCaughtMiddlewareContract>> throwableCaughtMiddleware,
        List<Class<? extends ProcessExitingMiddlewareContract>> processExitingMiddleware,
        List<ComponentProviderContract> providers,
        List<Consumer<ApplicationContract>> callbacks)
        implements CliConfigContract {

    public Config() {
        this(
                "App",
                System.getProperty("user.dir"),
                "1.0.0",
                "production",
                true,
                "UTC",
                "some_secret_app_key",
                "app/cli/data",
                "app.cli.data",
                "cli",
                "list",
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(new ComponentProvider()),
                List.of(ComponentProvider::publish));
    }
}
