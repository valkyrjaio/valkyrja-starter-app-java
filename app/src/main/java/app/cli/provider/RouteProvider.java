/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.cli.provider;

import app.cli.command.TestCommand;
import io.valkyrja.application.data.contract.CliConfigContract;
import io.valkyrja.cli.interaction.output.contract.OutputContract;
import io.valkyrja.cli.routing.data.contract.RouteContract;
import io.valkyrja.cli.routing.provider.contract.CliRouteProviderContract;
import io.valkyrja.container.manager.contract.ContainerContract;

import java.util.List;
import java.util.Map;

public final class RouteProvider implements CliRouteProviderContract {

    public static OutputContract testCommandHandler(ContainerContract c, Map<String, Object> args) {
        return c.getSingleton(TestCommand.class).run(
                c.getSingleton(CliConfigContract.class));
    }

    @Override
    public List<Class<?>> getControllerClasses() {
        return List.of(
                TestCommand.class);
    }

    @Override
    public List<RouteContract> getRoutes() {
        return List.of();
    }
}
