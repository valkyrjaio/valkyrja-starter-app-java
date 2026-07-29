/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.grpc;

import io.valkyrja.application.entry.tomcat.TomcatGrpc;
import org.apache.catalina.LifecycleException;

/**
 * Tomcat gRPC entry point.
 *
 * <p>Bootstraps the application once, then serves every call from an embedded Tomcat over HTTP/2
 * via the grpc-servlet transport. The gRPC configuration is shared with the other runtimes — only
 * the server that drives it differs.
 */
public class TomcatApp extends TomcatGrpc {

    public static void main(String[] args) throws LifecycleException {
        run(new Config());
    }
}
