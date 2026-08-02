/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.grpc;

import io.valkyrja.application.entry.netty.NettyGrpc;
import java.io.IOException;

/**
 * Netty gRPC entry point.
 *
 * <p>Bootstraps the application once, then serves every call from a Netty gRPC server over HTTP/2.
 * The gRPC configuration is shared with the other runtimes — only the server that drives it
 * differs.
 */
public class NettyApp extends NettyGrpc {

    public static void main(String[] args) throws IOException, InterruptedException {
        run(new Config());
    }
}
