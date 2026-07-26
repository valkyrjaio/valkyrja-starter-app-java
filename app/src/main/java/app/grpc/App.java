/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.grpc;

import io.valkyrja.application.entry.abstract_.WorkerGrpc;

public class App extends WorkerGrpc {

    public static void main(String[] args) {
        // Bootstrap the application once. Attach a transport adapter from the entry modules
        // (NettyGrpc / TomcatGrpc / JettyGrpc) to serve the bootstrapped application over HTTP/2.
        bootstrap(new Config());
    }
}
