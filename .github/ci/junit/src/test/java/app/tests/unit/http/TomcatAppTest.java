/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import app.http.TomcatApp;
import org.junit.jupiter.api.Test;

/**
 * Test the {@link TomcatApp} entry point.
 *
 * <p>The entry's {@code main} blocks on the Tomcat server loop, so it cannot run in-process; the
 * live request path is covered end to end by {@code app.tests.functional.entry.TomcatAppTest}.
 */
final class TomcatAppTest {

    @Test
    void isInstantiable() {
        assertNotNull(new TomcatApp());
    }
}
