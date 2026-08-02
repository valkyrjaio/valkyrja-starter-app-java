/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.http;

import io.valkyrja.application.entry.tomcat.TomcatHttp;
import org.apache.catalina.LifecycleException;

/**
 * Tomcat entry point.
 *
 * <p>Bootstraps the application once, then serves every request from the embedded Tomcat worker
 * runtime. The HTTP configuration is shared with the other runtimes — only the server that drives
 * it differs.
 */
public class TomcatApp extends TomcatHttp {

    public static void main(String[] args) throws LifecycleException {
        run(new Config());
    }
}
