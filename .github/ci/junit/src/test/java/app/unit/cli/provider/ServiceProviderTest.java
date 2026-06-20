/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.unit.cli.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import app.cli.command.TestCommand;
import app.cli.provider.ServiceProvider;
import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.output.factory.OutputFactory;
import io.valkyrja.cli.interaction.output.factory.contract.OutputFactoryContract;
import io.valkyrja.container.manager.Container;
import org.junit.jupiter.api.Test;

/** Test class. */
final class ServiceProviderTest {

    @Test
    void publishersExposesTestCommand() {
        assertEquals(1, new ServiceProvider().publishers().size());
    }

    @Test
    void publishTestCommandBindsCommand() {
        var container = new Container();
        container.setSingleton(InputContract.class, mock(InputContract.class));
        container.setSingleton(OutputFactoryContract.class, new OutputFactory());

        ServiceProvider.publishTestCommand(container);

        assertNotNull(container.getSingleton(TestCommand.class));
    }
}
