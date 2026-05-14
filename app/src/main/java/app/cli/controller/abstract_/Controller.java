/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.cli.controller.abstract_;

import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.output.factory.contract.OutputFactoryContract;

public abstract class Controller extends io.valkyrja.cli.routing.controller.Controller {

    public Controller(InputContract input, OutputFactoryContract outputFactory) {
        super(input, outputFactory);
    }
}
