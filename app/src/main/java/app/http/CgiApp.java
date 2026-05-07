/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http;

import io.valkyrja.application.entry.ExchangeCgiHttp;
import java.io.IOException;

/**
 * CGI-style entry point that re-bootstraps the application on every request.
 *
 * <p>Mimics CGI semantics: each incoming exchange gets a full bootstrap — clean
 * container isolation at the cost of re-bootstrapping overhead per request. Prefer
 * {@link App} (backed by {@link io.valkyrja.application.entry.ExchangeHttp}) for
 * production, which bootstraps once and uses an isolated child container per request.
 */
public class CgiApp extends ExchangeCgiHttp {

    static void main(String[] args) throws IOException {
        run(new Config());
    }
}
