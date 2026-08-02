/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.cli.command;

import app.cli.controller.abstract_.Controller;
import app.cli.provider.CliRouteProvider;
import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.message.Message;
import io.valkyrja.cli.interaction.output.contract.OutputContract;
import io.valkyrja.cli.interaction.output.factory.contract.OutputFactoryContract;
import io.valkyrja.cli.routing.attribute.ArgumentParameter;
import io.valkyrja.cli.routing.attribute.OptionParameter;
import io.valkyrja.cli.routing.attribute.Route;
import io.valkyrja.cli.routing.attribute.route.RouteHandler;
import io.valkyrja.cli.routing.enum_.ArgumentMode;
import io.valkyrja.cli.routing.enum_.ArgumentValueMode;
import io.valkyrja.cli.routing.enum_.OptionMode;
import io.valkyrja.cli.routing.enum_.OptionValueMode;
import java.util.List;

/**
 * Demonstrates every argument and option permutation the CLI router supports.
 *
 * <p>Each command echoes back the value(s) bound to its parameters so the declaration, the
 * generated command data, and the runtime binding can all be asserted.
 */
public class RoutingPermutationsCommand extends Controller {

    public RoutingPermutationsCommand(InputContract input, OutputFactoryContract outputFactory) {
        super(input, outputFactory);
    }

    /** A required, single-value argument. */
    @Route(name = "permutations:argument-required", description = "A required argument")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsArgumentRequiredHandler")
    @ArgumentParameter(
            name = "value",
            description = "A required single value argument",
            mode = ArgumentMode.REQUIRED,
            valueMode = ArgumentValueMode.DEFAULT)
    public OutputContract argumentRequired(String value) {
        return message("argument-required:" + value);
    }

    /** An optional, single-value argument. */
    @Route(name = "permutations:argument-optional", description = "An optional argument")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsArgumentOptionalHandler")
    @ArgumentParameter(
            name = "value",
            description = "An optional single value argument",
            mode = ArgumentMode.OPTIONAL,
            valueMode = ArgumentValueMode.DEFAULT)
    public OutputContract argumentOptional(String value) {
        return message("argument-optional:" + value);
    }

    /** An optional array argument, which consumes every remaining input argument. */
    @Route(name = "permutations:argument-array", description = "An array argument")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsArgumentArrayHandler")
    @ArgumentParameter(
            name = "values",
            description = "An optional array argument",
            mode = ArgumentMode.OPTIONAL,
            valueMode = ArgumentValueMode.ARRAY)
    public OutputContract argumentArray(List<String> values) {
        return message("argument-array:" + String.join(",", values));
    }

    /** A required array argument. */
    @Route(name = "permutations:argument-required-array", description = "A required array argument")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsArgumentRequiredArrayHandler")
    @ArgumentParameter(
            name = "values",
            description = "A required array argument",
            mode = ArgumentMode.REQUIRED,
            valueMode = ArgumentValueMode.ARRAY)
    public OutputContract argumentRequiredArray(List<String> values) {
        return message("argument-required-array:" + String.join(",", values));
    }

    /** An optional option that takes no value at all — a flag. */
    @Route(name = "permutations:option-none", description = "A flag option")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsOptionNoneHandler")
    @OptionParameter(
            name = "flag",
            description = "An optional valueless flag option",
            mode = OptionMode.OPTIONAL,
            valueMode = OptionValueMode.NONE)
    public OutputContract optionNone(boolean isProvided) {
        return message("option-none:" + (isProvided ? "yes" : "no"));
    }

    /** An optional option that takes a single value. */
    @Route(name = "permutations:option-default", description = "A single value option")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsOptionDefaultHandler")
    @OptionParameter(
            name = "value",
            description = "An optional single value option",
            mode = OptionMode.OPTIONAL,
            valueMode = OptionValueMode.DEFAULT)
    public OutputContract optionDefault(String value) {
        return message("option-default:" + value);
    }

    /** An optional option that can be repeated. */
    @Route(name = "permutations:option-array", description = "A repeatable option")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsOptionArrayHandler")
    @OptionParameter(
            name = "tag",
            description = "An optional repeatable option",
            mode = OptionMode.OPTIONAL,
            valueMode = OptionValueMode.ARRAY)
    public OutputContract optionArray(List<String> values) {
        return message("option-array:" + String.join(",", values));
    }

    /** A required option that takes a single value. */
    @Route(name = "permutations:option-required", description = "A required option")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsOptionRequiredHandler")
    @OptionParameter(
            name = "value",
            description = "A required single value option",
            mode = OptionMode.REQUIRED,
            valueMode = OptionValueMode.DEFAULT)
    public OutputContract optionRequired(String value) {
        return message("option-required:" + value);
    }

    /** A required flag option that takes no value. */
    @Route(name = "permutations:option-required-none", description = "A required flag option")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsOptionRequiredNoneHandler")
    @OptionParameter(
            name = "flag",
            description = "A required valueless flag option",
            mode = OptionMode.REQUIRED,
            valueMode = OptionValueMode.NONE)
    public OutputContract optionRequiredNone(boolean isProvided) {
        return message("option-required-none:" + (isProvided ? "yes" : "no"));
    }

    /** A required option that can be repeated. */
    @Route(
            name = "permutations:option-required-array",
            description = "A required repeatable option")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsOptionRequiredArrayHandler")
    @OptionParameter(
            name = "tag",
            description = "A required repeatable option",
            mode = OptionMode.REQUIRED,
            valueMode = OptionValueMode.ARRAY)
    public OutputContract optionRequiredArray(List<String> values) {
        return message("option-required-array:" + String.join(",", values));
    }

    /**
     * An option that may also be given by one of its short names.
     *
     * <p>Short names must avoid the framework's global ones — {@code h} (help), {@code v}
     * (version), {@code q} (quiet), {@code s} (silent), {@code N} (no interaction) and {@code t}
     * (token) — because the global middleware handles those before a command is dispatched.
     */
    @Route(name = "permutations:option-short", description = "An option with short names")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsOptionShortHandler")
    @OptionParameter(
            name = "marker",
            description = "An option with short names",
            shortNames = {"m"},
            mode = OptionMode.OPTIONAL,
            valueMode = OptionValueMode.NONE)
    public OutputContract optionShort(boolean isProvided) {
        return message("option-short:" + (isProvided ? "yes" : "no"));
    }

    /** An option restricted to a fixed set of valid values. */
    @Route(name = "permutations:option-valid-values", description = "An option with valid values")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsOptionValidValuesHandler")
    @OptionParameter(
            name = "format",
            description = "An option restricted to valid values",
            validValues = {"json", "xml"},
            mode = OptionMode.OPTIONAL,
            valueMode = OptionValueMode.DEFAULT)
    public OutputContract optionValidValues(String value) {
        return message("option-valid-values:" + value);
    }

    /** An option that falls back to its declared default when it is not given. */
    @Route(name = "permutations:option-default-value", description = "An option with a default")
    @RouteHandler(
            handlerClass = CliRouteProvider.class,
            handlerMethod = "permutationsOptionDefaultValueHandler")
    @OptionParameter(
            name = "value",
            description = "An option with a default value",
            defaultValue = "fallback",
            mode = OptionMode.OPTIONAL,
            valueMode = OptionValueMode.DEFAULT)
    public OutputContract optionDefaultValue(String value) {
        return message("option-default-value:" + value);
    }

    /** Arguments and options declared together on one command. */
    @Route(name = "permutations:mixed", description = "Arguments and options together")
    @RouteHandler(handlerClass = CliRouteProvider.class, handlerMethod = "permutationsMixedHandler")
    @ArgumentParameter(
            name = "name",
            description = "A required single value argument",
            mode = ArgumentMode.REQUIRED,
            valueMode = ArgumentValueMode.DEFAULT)
    @OptionParameter(
            name = "tag",
            description = "An optional repeatable option",
            mode = OptionMode.OPTIONAL,
            valueMode = OptionValueMode.ARRAY)
    public OutputContract mixed(String name, List<String> tags) {
        return message("mixed:" + name + ":" + String.join(",", tags));
    }

    /** Build a single message output. */
    protected OutputContract message(String text) {
        return outputFactory.createOutput().withAddedMessages(new Message(text));
    }
}
