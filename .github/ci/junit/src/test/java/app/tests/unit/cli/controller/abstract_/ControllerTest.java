/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.cli.controller.abstract_;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import app.cli.controller.abstract_.Controller;
import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.output.factory.contract.OutputFactoryContract;
import org.junit.jupiter.api.Test;

/** Test class. */
final class ControllerTest {

    @Test
    void subclassConstructsThroughBase() {
        var controller =
                new Controller(mock(InputContract.class), mock(OutputFactoryContract.class)) {};

        assertNotNull(controller);
    }
}
