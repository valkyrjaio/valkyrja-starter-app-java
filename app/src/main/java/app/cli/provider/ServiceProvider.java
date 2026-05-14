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
import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.output.factory.contract.OutputFactoryContract;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.container.provider.contract.ServiceProviderContract;

import java.util.Map;
import java.util.function.Consumer;

public final class ServiceProvider implements ServiceProviderContract {

    @Override
    public Map<Class<?>, Consumer<ContainerContract>> publishers() {
        return Map.of(
                TestCommand.class, ServiceProvider::publishTestCommand);
    }

    public static void publishTestCommand(ContainerContract container) {
        container.setSingleton(
                TestCommand.class,
                new TestCommand(
                        container.getSingleton(InputContract.class),
                        container.getSingleton(OutputFactoryContract.class)));
    }
}
