/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.tests.unit.http;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import app.http.NettyApp;
import org.junit.jupiter.api.Test;

/**
 * Test the {@link NettyApp} entry point.
 *
 * <p>The entry's {@code main} blocks on the Netty server loop, so it cannot run in-process; the
 * live request path is covered end to end by {@code app.tests.functional.entry.NettyAppTest}.
 */
final class NettyAppTest {

    @Test
    void isInstantiable() {
        assertNotNull(new NettyApp());
    }
}
