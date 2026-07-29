/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.cli.command;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import app.cli.command.TestCommand;
import io.valkyrja.application.data.contract.CliConfigContract;
import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.message.contract.AnswerContract;
import io.valkyrja.cli.interaction.output.factory.OutputFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/** Test class. */
final class TestCommandTest {

    private TestCommand command() {
        return new TestCommand(mock(InputContract.class), new OutputFactory());
    }

    @Test
    void runBuildsQuestionOutput() {
        var config = mock(CliConfigContract.class);
        when(config.namespace()).thenReturn("App");
        when(config.version()).thenReturn("1.0.0");

        assertNotNull(command().run(config));
    }

    @Test
    void answeredHandlesYesAndNo() {
        var command = command();
        var output = new OutputFactory().createOutput();
        var yes = mock(AnswerContract.class);
        when(yes.getUserResponse()).thenReturn("yes");
        var no = mock(AnswerContract.class);
        when(no.getUserResponse()).thenReturn("no");

        var original = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));
        try {
            assertNotNull(command.answered(output, yes));
            assertNotNull(command.answered(output, no));
        } finally {
            System.setOut(original);
        }
    }
}
