/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.cli;

import io.valkyrja.application.entry.Cli;

public class App extends Cli {

    static void main(String[] args) {
        run(new Config(), args);
    }
}
