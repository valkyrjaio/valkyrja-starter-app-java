/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.http.provider;

import app.http.controller.HomeController;
import app.http.controller.RoutingPermutationsController;
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.container.manager.contract.ContainerContract;
import io.valkyrja.http.message.response.contract.ResponseContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import io.valkyrja.http.routing.data.contract.DynamicRouteContract;
import io.valkyrja.http.routing.data.contract.RouteContract;
import io.valkyrja.http.routing.provider.contract.HttpRouteProviderContract;
import java.util.List;

public final class HttpRouteProvider implements HttpRouteProviderContract {

    public static ResponseContract versionHandler(
            ContainerContract container, RouteContract route) {
        return HomeController.version(
                container.getSingleton(ApplicationContract.class),
                container.getSingleton(ResponseFactoryContract.class));
    }

    public static ResponseContract textHandler(ContainerContract container, RouteContract route) {
        return HomeController.text();
    }

    public static ResponseContract welcomeHandler(
            ContainerContract container, RouteContract route) {
        return container.getSingleton(HomeController.class).welcome();
    }

    public static ResponseContract welcomeCachedHandler(
            ContainerContract container, RouteContract route) {
        return container.getSingleton(HomeController.class).welcomeCached();
    }

    public static ResponseContract homeHandler(ContainerContract container, RouteContract route) {
        return container.getSingleton(HomeController.class).home();
    }

    public static ResponseContract jsonHandler(ContainerContract container, RouteContract route) {
        return container.getSingleton(HomeController.class).json();
    }

    public static ResponseContract permutationsNumHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).num(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsIdHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).id(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsSlugHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).slug(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsAlphaHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).alpha(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsAlphaLowercaseHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).alphaLowercase(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsAlphaUppercaseHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).alphaUppercase(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsAlphaNumHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).alphaNum(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsAlphaNumUnderscoreHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).alphaNumUnderscore(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsAnyHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).any(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsUuidHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).uuid(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsUlidHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).ulid(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsVlidHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).vlid(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsOptionalHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).optional(parameterValue(route, "value"));
    }

    public static ResponseContract permutationsMultiHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container)
                .multi(parameterValue(route, "first"), parameterValue(route, "second"));
    }

    public static ResponseContract permutationsNonCaptureHandler(
            ContainerContract container, RouteContract route) {
        // The parameter is matched but not captured, so there is no value to bind.
        return permutations(container).nonCapture();
    }

    public static ResponseContract permutationsStaticHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).staticRoute();
    }

    public static ResponseContract permutationsPostHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).post();
    }

    public static ResponseContract permutationsAnyMethodHandler(
            ContainerContract container, RouteContract route) {
        return permutations(container).anyMethod();
    }

    @Override
    public List<Class<?>> getControllerClasses() {
        return List.of(HomeController.class, RoutingPermutationsController.class);
    }

    @Override
    public List<RouteContract> getRoutes() {
        return List.of();
    }

    /** Resolve the routing permutations controller. */
    private static RoutingPermutationsController permutations(ContainerContract container) {
        return container.getSingleton(RoutingPermutationsController.class);
    }

    /** Read a bound parameter's value from a dynamic route, or an empty string when unbound. */
    private static String parameterValue(RouteContract route, String name) {
        Object value = ((DynamicRouteContract) route).getParameter(name).getValue();

        return value == null ? "" : value.toString();
    }
}
