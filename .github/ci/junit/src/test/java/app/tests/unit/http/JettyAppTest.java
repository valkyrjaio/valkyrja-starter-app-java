/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import app.http.JettyApp;
import org.junit.jupiter.api.Test;

/**
 * Test the {@link JettyApp} entry point.
 *
 * <p>The entry's {@code main} blocks on the Jetty server loop, so it cannot run in-process; the
 * live request path is covered end to end by {@code app.tests.functional.entry.JettyAppTest}.
 */
final class JettyAppTest {

    @Test
    void isInstantiable() {
        assertNotNull(new JettyApp());
    }
}
