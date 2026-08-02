/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.cli.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import app.cli.command.RoutingPermutationsCommand;
import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.output.contract.OutputContract;
import io.valkyrja.cli.interaction.output.factory.OutputFactory;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Test the routing permutations command. */
final class RoutingPermutationsCommandTest {

    private final RoutingPermutationsCommand command =
            new RoutingPermutationsCommand(mock(InputContract.class), new OutputFactory());

    private String text(OutputContract output) {
        return output.getMessages().get(0).getText();
    }

    @Test
    void argumentPermutationsEchoTheirValues() {
        assertEquals("argument-required:foo", text(command.argumentRequired("foo")));
        assertEquals("argument-optional:bar", text(command.argumentOptional("bar")));
        assertEquals("argument-array:a,b", text(command.argumentArray(List.of("a", "b"))));
        assertEquals(
                "argument-required-array:x", text(command.argumentRequiredArray(List.of("x"))));
    }

    @Test
    void optionPermutationsEchoTheirValues() {
        assertEquals("option-none:yes", text(command.optionNone(true)));
        assertEquals("option-none:no", text(command.optionNone(false)));
        assertEquals("option-default:hello", text(command.optionDefault("hello")));
        assertEquals("option-array:x,y", text(command.optionArray(List.of("x", "y"))));
        assertEquals("option-required:req", text(command.optionRequired("req")));
        assertEquals("option-required-none:yes", text(command.optionRequiredNone(true)));
        assertEquals(
                "option-required-array:one", text(command.optionRequiredArray(List.of("one"))));
        assertEquals("option-short:yes", text(command.optionShort(true)));
        assertEquals("option-short:no", text(command.optionShort(false)));
        assertEquals("option-valid-values:json", text(command.optionValidValues("json")));
        assertEquals("option-default-value:fallback", text(command.optionDefaultValue("fallback")));
    }

    @Test
    void mixedCombinesArgumentsAndOptions() {
        assertEquals("mixed:bob:t1,t2", text(command.mixed("bob", List.of("t1", "t2"))));
    }
}
