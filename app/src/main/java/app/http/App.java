/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.http;

import io.valkyrja.application.entry.exchange.ExchangeHttp;
import java.io.IOException;

public class App extends ExchangeHttp {

    public static void main(String[] args) throws IOException {
        run(new Config());
    }
}
