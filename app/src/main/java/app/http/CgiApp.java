/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.http;

import io.valkyrja.application.entry.exchange.ExchangeCgiHttp;
import java.io.IOException;

/**
 * CGI-style entry point that re-bootstraps the application on every request.
 *
 * <p>Mimics CGI semantics: each incoming exchange gets a full bootstrap — clean container isolation
 * at the cost of re-bootstrapping overhead per request. Prefer {@link App} (backed by {@link
 * io.valkyrja.application.entry.exchange.ExchangeHttp}) for production, which bootstraps once and
 * uses an isolated child container per request.
 */
public class CgiApp extends ExchangeCgiHttp {

    static void main(String[] args) throws IOException {
        run(new Config());
    }
}
