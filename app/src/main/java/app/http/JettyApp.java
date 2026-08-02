/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http;

import io.valkyrja.application.entry.jetty.JettyHttp;

/**
 * Jetty entry point.
 *
 * <p>Bootstraps the application once, then serves every request from the Jetty worker runtime. The
 * HTTP configuration is shared with the other runtimes — only the server that drives it differs.
 */
public class JettyApp extends JettyHttp {

    public static void main(String[] args) throws Exception {
        run(new Config());
    }
}
