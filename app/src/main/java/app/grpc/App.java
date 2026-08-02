/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
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
