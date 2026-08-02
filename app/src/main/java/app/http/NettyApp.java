/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.http;

import io.valkyrja.application.entry.netty.NettyHttp;

/**
 * Netty entry point.
 *
 * <p>Bootstraps the application once, then serves every request from the Netty worker runtime. The
 * HTTP configuration is shared with the other runtimes — only the server that drives it differs.
 */
public class NettyApp extends NettyHttp {

    public static void main(String[] args) throws InterruptedException {
        run(new Config());
    }
}
