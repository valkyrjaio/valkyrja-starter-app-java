/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.cli.controller.abstract_;

import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.output.factory.contract.OutputFactoryContract;

public abstract class Controller extends io.valkyrja.cli.routing.controller.Controller {

    public Controller(InputContract input, OutputFactoryContract outputFactory) {
        super(input, outputFactory);
    }
}
