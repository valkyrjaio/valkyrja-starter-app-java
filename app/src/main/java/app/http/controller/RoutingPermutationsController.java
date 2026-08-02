/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http.controller;

import app.http.controller.abstract_.Controller;
import app.http.provider.HttpRouteProvider;
import io.valkyrja.http.message.enum_.RequestMethod;
import io.valkyrja.http.message.enum_.StatusCode;
import io.valkyrja.http.message.header.collection.HeaderCollection;
import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.TextResponse;
import io.valkyrja.http.message.response.contract.TextResponseContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import io.valkyrja.http.routing.attribute.Parameter;
import io.valkyrja.http.routing.attribute.Route;
import io.valkyrja.http.routing.attribute.route.RouteHandler;
import io.valkyrja.http.routing.constant.Regex;

/**
 * Demonstrates every routing permutation the framework supports.
 *
 * <p>Each route echoes back the value(s) bound to its parameters so the produced regex, the match,
 * and the parameter binding can all be asserted end to end. Paths are namespaced under {@code
 * /permutations} so they never collide with the application's other routes.
 */
public class RoutingPermutationsController extends Controller {

    public RoutingPermutationsController(
            ServerRequestContract request, ResponseFactoryContract responseFactory) {
        super(request, responseFactory);
    }

    /** A numeric parameter. */
    @Route(path = "/permutations/num/{value}", name = "permutations.num")
    @Parameter(name = "value", regex = Regex.NUM)
    @RouteHandler(handlerClass = HttpRouteProvider.class, handlerMethod = "permutationsNumHandler")
    public TextResponseContract num(String value) {
        return text(value);
    }

    /** An id parameter. */
    @Route(path = "/permutations/id/{value}", name = "permutations.id")
    @Parameter(name = "value", regex = Regex.ID)
    @RouteHandler(handlerClass = HttpRouteProvider.class, handlerMethod = "permutationsIdHandler")
    public TextResponseContract id(String value) {
        return text(value);
    }

    /** A slug parameter. */
    @Route(path = "/permutations/slug/{value}", name = "permutations.slug")
    @Parameter(name = "value", regex = Regex.SLUG)
    @RouteHandler(handlerClass = HttpRouteProvider.class, handlerMethod = "permutationsSlugHandler")
    public TextResponseContract slug(String value) {
        return text(value);
    }

    /** An alphabetic parameter. */
    @Route(path = "/permutations/alpha/{value}", name = "permutations.alpha")
    @Parameter(name = "value", regex = Regex.ALPHA)
    @RouteHandler(
            handlerClass = HttpRouteProvider.class,
            handlerMethod = "permutationsAlphaHandler")
    public TextResponseContract alpha(String value) {
        return text(value);
    }

    /** A lowercase alphabetic parameter. */
    @Route(path = "/permutations/alpha-lowercase/{value}", name = "permutations.alphaLowercase")
    @Parameter(name = "value", regex = Regex.ALPHA_LOWERCASE)
    @RouteHandler(
            handlerClass = HttpRouteProvider.class,
            handlerMethod = "permutationsAlphaLowercaseHandler")
    public TextResponseContract alphaLowercase(String value) {
        return text(value);
    }

    /** An uppercase alphabetic parameter. */
    @Route(path = "/permutations/alpha-uppercase/{value}", name = "permutations.alphaUppercase")
    @Parameter(name = "value", regex = Regex.ALPHA_UPPERCASE)
    @RouteHandler(
            handlerClass = HttpRouteProvider.class,
            handlerMethod = "permutationsAlphaUppercaseHandler")
    public TextResponseContract alphaUppercase(String value) {
        return text(value);
    }

    /** An alphanumeric parameter. */
    @Route(path = "/permutations/alpha-num/{value}", name = "permutations.alphaNum")
    @Parameter(name = "value", regex = Regex.ALPHA_NUM)
    @RouteHandler(
            handlerClass = HttpRouteProvider.class,
            handlerMethod = "permutationsAlphaNumHandler")
    public TextResponseContract alphaNum(String value) {
        return text(value);
    }

    /** An alphanumeric parameter that also allows underscores. */
    @Route(
            path = "/permutations/alpha-num-underscore/{value}",
            name = "permutations.alphaNumUnderscore")
    @Parameter(name = "value", regex = Regex.ALPHA_NUM_UNDERSCORE)
    @RouteHandler(
            handlerClass = HttpRouteProvider.class,
            handlerMethod = "permutationsAlphaNumUnderscoreHandler")
    public TextResponseContract alphaNumUnderscore(String value) {
        return text(value);
    }

    /** A parameter that matches anything. */
    @Route(path = "/permutations/any/{value}", name = "permutations.any")
    @Parameter(name = "value", regex = Regex.ANY)
    @RouteHandler(handlerClass = HttpRouteProvider.class, handlerMethod = "permutationsAnyHandler")
    public TextResponseContract any(String value) {
        return text(value);
    }

    /** A uuid parameter. */
    @Route(path = "/permutations/uuid/{value}", name = "permutations.uuid")
    @Parameter(name = "value", regex = Regex.UUID)
    @RouteHandler(handlerClass = HttpRouteProvider.class, handlerMethod = "permutationsUuidHandler")
    public TextResponseContract uuid(String value) {
        return text(value);
    }

    /** A ulid parameter. */
    @Route(path = "/permutations/ulid/{value}", name = "permutations.ulid")
    @Parameter(name = "value", regex = Regex.ULID)
    @RouteHandler(handlerClass = HttpRouteProvider.class, handlerMethod = "permutationsUlidHandler")
    public TextResponseContract ulid(String value) {
        return text(value);
    }

    /** A vlid parameter. */
    @Route(path = "/permutations/vlid/{value}", name = "permutations.vlid")
    @Parameter(name = "value", regex = Regex.VLID)
    @RouteHandler(handlerClass = HttpRouteProvider.class, handlerMethod = "permutationsVlidHandler")
    public TextResponseContract vlid(String value) {
        return text(value);
    }

    /** An optional parameter, which the route still matches when it is absent. */
    @Route(path = "/permutations/optional/{value?}", name = "permutations.optional")
    @Parameter(name = "value", regex = Regex.ALPHA, isOptional = true)
    @RouteHandler(
            handlerClass = HttpRouteProvider.class,
            handlerMethod = "permutationsOptionalHandler")
    public TextResponseContract optional(String value) {
        return text(value.isEmpty() ? "absent" : value);
    }

    /** Multiple parameters separated by a static segment. */
    @Route(path = "/permutations/multi/{first}/{second}", name = "permutations.multi")
    @Parameter(name = "first", regex = Regex.NUM)
    @Parameter(name = "second", regex = Regex.ALPHA)
    @RouteHandler(
            handlerClass = HttpRouteProvider.class,
            handlerMethod = "permutationsMultiHandler")
    public TextResponseContract multi(String first, String second) {
        return text(first + "-" + second);
    }

    /** A parameter that is matched but deliberately not captured. */
    @Route(path = "/permutations/non-capture/{value}", name = "permutations.nonCapture")
    @Parameter(name = "value", regex = Regex.ALPHA, shouldCapture = false)
    @RouteHandler(
            handlerClass = HttpRouteProvider.class,
            handlerMethod = "permutationsNonCaptureHandler")
    public TextResponseContract nonCapture() {
        return text("non-capture");
    }

    /** A route with no parameters at all. */
    @Route(path = "/permutations/static", name = "permutations.static")
    @RouteHandler(
            handlerClass = HttpRouteProvider.class,
            handlerMethod = "permutationsStaticHandler")
    public TextResponseContract staticRoute() {
        return text("static");
    }

    /** A route restricted to a single request method. */
    @Route(
            path = "/permutations/post",
            name = "permutations.post",
            requestMethods = {RequestMethod.POST})
    @RouteHandler(handlerClass = HttpRouteProvider.class, handlerMethod = "permutationsPostHandler")
    public TextResponseContract post() {
        return text("post");
    }

    /** A route that answers to every request method. */
    @Route(
            path = "/permutations/any-method",
            name = "permutations.anyMethod",
            requestMethods = {RequestMethod.ANY})
    @RouteHandler(
            handlerClass = HttpRouteProvider.class,
            handlerMethod = "permutationsAnyMethodHandler")
    public TextResponseContract anyMethod() {
        return text("any-method");
    }

    /** Build a plain text response. */
    protected TextResponseContract text(String body) {
        return new TextResponse(body, StatusCode.OK, new HeaderCollection());
    }
}
