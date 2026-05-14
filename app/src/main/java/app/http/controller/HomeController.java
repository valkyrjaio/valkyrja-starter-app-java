/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http.controller;

import app.http.controller.abstract_.Controller;
import app.http.provider.RouteProvider;
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.http.message.enum_.RequestMethod;
import io.valkyrja.http.message.enum_.StatusCode;
import io.valkyrja.http.message.header.collection.HeaderCollection;
import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.TextResponse;
import io.valkyrja.http.message.response.contract.JsonResponseContract;
import io.valkyrja.http.message.response.contract.ResponseContract;
import io.valkyrja.http.message.response.contract.TextResponseContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import io.valkyrja.http.routing.attribute.Route;
import io.valkyrja.http.routing.attribute.route.RouteHandler;

import java.util.Map;

public class HomeController extends Controller {

    public HomeController(ServerRequestContract request, ResponseFactoryContract responseFactory) {
        super(request, responseFactory);
    }

    @Route(path = "/version", name = "version", requestMethods = {RequestMethod.GET})
    @Route(path = "/version", name = "version.post", requestMethods = {RequestMethod.POST})
    @Route(path = "/version", name = "version.put", requestMethods = {RequestMethod.PUT})
    @RouteHandler(handlerClass = RouteProvider.class, handlerMethod = "versionHandler")
    public static TextResponseContract version(
            ApplicationContract app, ResponseFactoryContract responseFactory) {
        return responseFactory.createTextResponse(app.getVersion(), StatusCode.OK, new HeaderCollection());
    }

    @Route(path = "/text", name = "text", requestMethods = {RequestMethod.GET})
    @RouteHandler(handlerClass = RouteProvider.class, handlerMethod = "textHandler")
    public static TextResponseContract text() {
        return new TextResponse("Hello World!", StatusCode.OK, new HeaderCollection());
    }

    @Route(path = "/", name = "welcome")
    @RouteHandler(handlerClass = RouteProvider.class, handlerMethod = "welcomeHandler")
    public ResponseContract welcome() {
        return new TextResponse("Welcome!", StatusCode.OK, new HeaderCollection());
    }

    @Route(path = "/cached", name = "welcome.cached")
    @RouteHandler(handlerClass = RouteProvider.class, handlerMethod = "welcomeCachedHandler")
    public ResponseContract welcomeCached() {
        return new TextResponse("Welcome (cached)!", StatusCode.OK, new HeaderCollection());
    }

    @Route(path = "/home", name = "home")
    @RouteHandler(handlerClass = RouteProvider.class, handlerMethod = "homeHandler")
    public ResponseContract home() {
        return new TextResponse("Home!", StatusCode.OK, new HeaderCollection());
    }

    @Route(path = "/json", name = "json")
    @RouteHandler(handlerClass = RouteProvider.class, handlerMethod = "jsonHandler")
    public JsonResponseContract json() {
        return responseFactory.createJsonResponse(
                Map.of("message", "Json response example"), StatusCode.OK, new HeaderCollection());
    }
}
