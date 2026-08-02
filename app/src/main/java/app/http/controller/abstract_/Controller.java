/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.http.controller.abstract_;

import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;

public abstract class Controller extends io.valkyrja.http.routing.controller.Controller {

    public Controller(ServerRequestContract request, ResponseFactoryContract responseFactory) {
        super(request, responseFactory);
    }
}
