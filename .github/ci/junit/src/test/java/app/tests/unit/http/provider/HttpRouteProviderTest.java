/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import app.http.controller.HomeController;
import app.http.controller.RoutingPermutationsController;
import app.http.provider.HttpRouteProvider;
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.container.manager.Container;
import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import io.valkyrja.http.routing.data.contract.RouteContract;
import org.junit.jupiter.api.Test;

/** Test class. */
final class HttpRouteProviderTest {

    private Container container() {
        var container = new Container();
        container.setSingleton(ApplicationContract.class, mock(ApplicationContract.class));
        container.setSingleton(ResponseFactoryContract.class, mock(ResponseFactoryContract.class));
        container.setSingleton(
                HomeController.class,
                new HomeController(
                        mock(ServerRequestContract.class), mock(ResponseFactoryContract.class)));

        container.setSingleton(
                RoutingPermutationsController.class,
                new RoutingPermutationsController(
                        mock(ServerRequestContract.class), mock(ResponseFactoryContract.class)));

        return container;
    }

    /** Build a dynamic route whose parameters resolve to the given name/value pair. */
    private RouteContract dynamicRoute(java.util.Map<String, String> values) {
        var route = mock(io.valkyrja.http.routing.data.contract.DynamicRouteContract.class);
        org.mockito.Mockito.when(route.getParameter(org.mockito.ArgumentMatchers.anyString()))
                .thenAnswer(
                        invocation -> {
                            var parameter =
                                    mock(
                                            io.valkyrja.http.routing.data.contract.ParameterContract
                                                    .class);
                            org.mockito.Mockito.when(parameter.getValue())
                                    .thenReturn(values.get(invocation.getArgument(0)));

                            return parameter;
                        });

        return route;
    }

    @Test
    void exposesControllerAndEmptyRoutes() {
        var provider = new HttpRouteProvider();

        assertEquals(2, provider.getControllerClasses().size());
        assertTrue(provider.getControllerClasses().contains(RoutingPermutationsController.class));
        assertTrue(provider.getRoutes().isEmpty());
    }

    @Test
    void handlersDelegateToHomeController() {
        var container = container();
        var route = mock(RouteContract.class);

        HttpRouteProvider.versionHandler(container, route);
        assertNotNull(HttpRouteProvider.textHandler(container, route));
        assertNotNull(HttpRouteProvider.welcomeHandler(container, route));
        assertNotNull(HttpRouteProvider.welcomeCachedHandler(container, route));
        assertNotNull(HttpRouteProvider.homeHandler(container, route));
        HttpRouteProvider.jsonHandler(container, route);
    }

    @Test
    void permutationHandlersBindTheirRoutesParameters() {
        var container = container();
        var single = dynamicRoute(java.util.Map.of("value", "abc"));

        assertNotNull(HttpRouteProvider.permutationsNumHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsIdHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsSlugHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsAlphaHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsAlphaLowercaseHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsAlphaUppercaseHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsAlphaNumHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsAlphaNumUnderscoreHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsAnyHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsUuidHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsUlidHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsVlidHandler(container, single));
        assertNotNull(HttpRouteProvider.permutationsOptionalHandler(container, single));
        assertNotNull(
                HttpRouteProvider.permutationsMultiHandler(
                        container, dynamicRoute(java.util.Map.of("first", "12", "second", "two"))));
    }

    @Test
    void parameterlessPermutationHandlersProduceResponses() {
        var container = container();
        var route = mock(RouteContract.class);

        assertNotNull(HttpRouteProvider.permutationsNonCaptureHandler(container, route));
        assertNotNull(HttpRouteProvider.permutationsStaticHandler(container, route));
        assertNotNull(HttpRouteProvider.permutationsPostHandler(container, route));
        assertNotNull(HttpRouteProvider.permutationsAnyMethodHandler(container, route));
    }

    @Test
    void anUnboundParameterBindsAsAnEmptyValue() {
        var container = container();

        // An optional parameter that was not matched has no value; the route still responds.
        assertNotNull(
                HttpRouteProvider.permutationsOptionalHandler(
                        container, dynamicRoute(java.util.Map.of())));
    }
}
