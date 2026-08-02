/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
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
