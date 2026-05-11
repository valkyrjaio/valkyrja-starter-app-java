/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http;

import io.valkyrja.application.data.contract.HttpConfigContract;
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.application.provider.contract.ComponentProviderContract;
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
    List<Class<? extends ComponentProviderContract>> providers,
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
            List.of()
        );
    }
}
