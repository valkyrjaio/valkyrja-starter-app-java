/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tomcat;

import app.http.Config;
import io.valkyrja.application.entry.tomcat.TomcatHttp;
import org.apache.catalina.LifecycleException;

/**
 * Tomcat entry point.
 *
 * <p>Bootstraps the application once, then serves every request from the embedded Tomcat worker
 * runtime. The HTTP configuration is shared with the other runtimes — only the server that drives
 * it differs.
 */
public class App extends TomcatHttp {

    public static void main(String[] args) throws LifecycleException {
        run(new Config());
    }
}
