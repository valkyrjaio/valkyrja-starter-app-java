/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.cli.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import app.cli.command.RoutingPermutationsCommand;
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
        assertEquals(2, new ServiceProvider().publishers().size());
    }

    @Test
    void publishTestCommandBindsCommand() {
        var container = new Container();
        container.setSingleton(InputContract.class, mock(InputContract.class));
        container.setSingleton(OutputFactoryContract.class, new OutputFactory());

        ServiceProvider.publishTestCommand(container);

        assertNotNull(container.getSingleton(TestCommand.class));
    }

    @Test
    void publishRoutingPermutationsCommandBindsCommand() {
        var container = new Container();
        container.setSingleton(InputContract.class, mock(InputContract.class));
        container.setSingleton(OutputFactoryContract.class, new OutputFactory());

        ServiceProvider.publishRoutingPermutationsCommand(container);

        assertNotNull(container.getSingleton(RoutingPermutationsCommand.class));
    }
}
