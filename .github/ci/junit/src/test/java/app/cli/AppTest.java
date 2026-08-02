/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.cli;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import io.valkyrja.cli.server.support.Exiter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/** Test class. */
final class AppTest {

    @Test
    void mainBootsTheCliWithoutExitingTheJvm() {
        var originalOut = System.out;
        var originalIn = System.in;
        Exiter.freeze();
        System.setOut(new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));
        System.setIn(new ByteArrayInputStream(new byte[0]));
        try {
            assertDoesNotThrow(() -> App.main(new String[] {"list"}));
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
            Exiter.unfreeze();
        }
    }

    @Test
    void isInstantiable() {
        org.junit.jupiter.api.Assertions.assertNotNull(new App());
    }
}
