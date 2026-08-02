/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.cli.provider;

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
import org.junit.jupiter.api.Test;

/** Test class. */
final class CliRouteProviderTest {

    @Test
    void exposesControllerAndEmptyRoutes() {
        var provider = new CliRouteProvider();

        assertEquals(2, provider.getControllerClasses().size());
        assertTrue(provider.getRoutes().isEmpty());
    }

    @Test
    void testCommandHandlerRunsTheCommand() {
        var container = new Container();
        container.setSingleton(
                TestCommand.class, new TestCommand(mock(InputContract.class), new OutputFactory()));
        container.setSingleton(CliConfigContract.class, mock(CliConfigContract.class));

        assertNotNull(CliRouteProvider.testCommandHandler(container, mock(RouteContract.class)));
    }

    @Test
    void permutationHandlersBindTheirRoutesParameters() {
        var container = new io.valkyrja.container.manager.Container();
        container.setSingleton(
                app.cli.command.RoutingPermutationsCommand.class,
                new app.cli.command.RoutingPermutationsCommand(
                        mock(io.valkyrja.cli.interaction.input.contract.InputContract.class),
                        new io.valkyrja.cli.interaction.output.factory.OutputFactory()));

        var route = route(java.util.Map.of("value", "foo"), java.util.Map.of("tag", "x"));

        assertNotNull(CliRouteProvider.permutationsArgumentRequiredHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsArgumentOptionalHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsArgumentArrayHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsArgumentRequiredArrayHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionNoneHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionDefaultHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionArrayHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionRequiredHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionRequiredNoneHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionRequiredArrayHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionShortHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionValidValuesHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionDefaultValueHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsMixedHandler(container, route));
    }

    @Test
    void permutationHandlersReadProvidedOptionsRatherThanFallingBack() {
        var container = new io.valkyrja.container.manager.Container();
        container.setSingleton(
                app.cli.command.RoutingPermutationsCommand.class,
                new app.cli.command.RoutingPermutationsCommand(
                        mock(io.valkyrja.cli.interaction.input.contract.InputContract.class),
                        new io.valkyrja.cli.interaction.output.factory.OutputFactory()));

        // The route above binds none of these options, so every handler takes its "absent" path:
        // hasOption() returns false and the default-value handler falls back to the declared
        // default. Binding them here takes the other side of both branches.
        var route =
                route(
                        java.util.Map.of("name", "bob"),
                        java.util.Map.of(
                                "value", "given", "flag", "on", "marker", "on", "tag", "x"));

        assertNotNull(CliRouteProvider.permutationsOptionNoneHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionRequiredNoneHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionShortHandler(container, route));
        assertNotNull(CliRouteProvider.permutationsOptionDefaultValueHandler(container, route));
    }

    /** Build a route whose arguments and options resolve to the given name/value pairs. */
    private RouteContract route(
            java.util.Map<String, String> arguments, java.util.Map<String, String> options) {
        var route = mock(RouteContract.class);

        org.mockito.Mockito.when(route.getArgument(org.mockito.ArgumentMatchers.anyString()))
                .thenAnswer(
                        invocation ->
                                new io.valkyrja.cli.routing.data.ArgumentParameter(
                                        invocation.getArgument(0),
                                        "description",
                                        io.valkyrja.cli.routing.enum_.ArgumentMode.OPTIONAL,
                                        io.valkyrja.cli.routing.enum_.ArgumentValueMode.DEFAULT,
                                        arguments.containsKey(invocation.getArgument(0))
                                                ? java.util.List.of(
                                                        new io.valkyrja.cli.interaction.argument
                                                                .Argument(
                                                                arguments.get(
                                                                        invocation.getArgument(0))))
                                                : java.util.List.of()));

        org.mockito.Mockito.when(route.getOption(org.mockito.ArgumentMatchers.anyString()))
                .thenAnswer(
                        invocation ->
                                new io.valkyrja.cli.routing.data.OptionParameter(
                                        invocation.getArgument(0),
                                        "description",
                                        "",
                                        "fallback",
                                        java.util.List.of(),
                                        java.util.List.of(),
                                        options.containsKey(invocation.getArgument(0))
                                                ? java.util.List.of(
                                                        new io.valkyrja.cli.interaction.option
                                                                .Option(
                                                                invocation.getArgument(0),
                                                                options.get(
                                                                        invocation.getArgument(0)),
                                                                io.valkyrja.cli.interaction.enum_
                                                                        .OptionType.LONG))
                                                : java.util.List.of(),
                                        io.valkyrja.cli.routing.enum_.OptionMode.OPTIONAL,
                                        io.valkyrja.cli.routing.enum_.OptionValueMode.DEFAULT));

        return route;
    }
}
