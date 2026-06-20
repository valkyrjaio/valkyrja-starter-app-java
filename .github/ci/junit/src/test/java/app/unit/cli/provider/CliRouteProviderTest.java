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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import app.cli.command.TestCommand;
import app.cli.provider.CliRouteProvider;
import io.valkyrja.application.data.contract.CliConfigContract;
import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.output.factory.OutputFactory;
import io.valkyrja.cli.routing.data.contract.RouteContract;
import io.valkyrja.container.manager.Container;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** Test class. */
final class CliRouteProviderTest {

    @Test
    void exposesControllerAndEmptyRoutes() {
        var provider = new CliRouteProvider();

        assertEquals(1, provider.getControllerClasses().size());
        assertTrue(provider.getRoutes().isEmpty());
    }

    @Test
    void testCommandHandlerRunsTheCommand() {
        var container = new Container();
        container.setSingleton(
                TestCommand.class,
                new TestCommand(mock(InputContract.class), new OutputFactory()));
        container.setSingleton(CliConfigContract.class, mock(CliConfigContract.class));

        assertNotNull(CliRouteProvider.testCommandHandler(container, Map.of()));
    }
}
