/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.cli.provider;

import app.cli.command.RoutingPermutationsCommand;
import app.cli.command.TestCommand;
import io.valkyrja.application.data.contract.CliConfigContract;
import io.valkyrja.cli.interaction.output.contract.OutputContract;
import io.valkyrja.cli.routing.data.contract.RouteContract;
import io.valkyrja.cli.routing.provider.contract.CliRouteProviderContract;
import io.valkyrja.container.manager.contract.ContainerContract;
import java.util.List;

public final class CliRouteProvider implements CliRouteProviderContract {

    public static OutputContract testCommandHandler(ContainerContract c, RouteContract route) {
        return c.getSingleton(TestCommand.class).run(c.getSingleton(CliConfigContract.class));
    }

    public static OutputContract permutationsArgumentRequiredHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).argumentRequired(argumentValue(route, "value"));
    }

    public static OutputContract permutationsArgumentOptionalHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).argumentOptional(argumentValue(route, "value"));
    }

    public static OutputContract permutationsArgumentArrayHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).argumentArray(argumentValues(route, "values"));
    }

    public static OutputContract permutationsArgumentRequiredArrayHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).argumentRequiredArray(argumentValues(route, "values"));
    }

    public static OutputContract permutationsOptionNoneHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).optionNone(hasOption(route, "flag"));
    }

    public static OutputContract permutationsOptionDefaultHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).optionDefault(optionValue(route, "value"));
    }

    public static OutputContract permutationsOptionArrayHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).optionArray(optionValues(route, "tag"));
    }

    public static OutputContract permutationsOptionRequiredHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).optionRequired(optionValue(route, "value"));
    }

    public static OutputContract permutationsOptionRequiredNoneHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).optionRequiredNone(hasOption(route, "flag"));
    }

    public static OutputContract permutationsOptionRequiredArrayHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).optionRequiredArray(optionValues(route, "tag"));
    }

    public static OutputContract permutationsOptionShortHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).optionShort(hasOption(route, "marker"));
    }

    public static OutputContract permutationsOptionValidValuesHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).optionValidValues(optionValue(route, "format"));
    }

    public static OutputContract permutationsOptionDefaultValueHandler(
            ContainerContract container, RouteContract route) {
        var option = route.getOption("value");
        // An option that was not given falls back to the default it declared.
        String value = option.hasFirstValue() ? option.getFirstValue() : option.getDefaultValue();

        return permutations(container).optionDefaultValue(value);
    }

    public static OutputContract permutationsMixedHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container)
                .mixed(argumentValue(route, "name"), optionValues(route, "tag"));
    }

    /** Resolve the routing permutations command. */
    private static RoutingPermutationsCommand permutations(ContainerContract container) {
        return container.getSingleton(RoutingPermutationsCommand.class);
    }

    /** Get the first value bound to an argument. */
    private static String argumentValue(RouteContract route, String name) {
        return route.getArgument(name).getFirstValue();
    }

    /** Get every value bound to an argument. */
    private static List<String> argumentValues(RouteContract route, String name) {
        return route.getArgument(name).getArguments().stream()
                .map(io.valkyrja.cli.interaction.argument.contract.ArgumentContract::getValue)
                .toList();
    }

    /** Determine whether an option was provided. */
    private static boolean hasOption(RouteContract route, String name) {
        return !route.getOption(name).getOptions().isEmpty();
    }

    /** Get the first value bound to an option. */
    private static String optionValue(RouteContract route, String name) {
        return route.getOption(name).getFirstValue();
    }

    /** Get every value bound to an option. */
    private static List<String> optionValues(RouteContract route, String name) {
        return route.getOption(name).getOptions().stream()
                .map(io.valkyrja.cli.interaction.option.contract.OptionContract::getValue)
                .toList();
    }

    @Override
    public List<Class<?>> getControllerClasses() {
        return List.of(TestCommand.class, RoutingPermutationsCommand.class);
    }

    @Override
    public List<RouteContract> getRoutes() {
        return List.of();
    }
}
