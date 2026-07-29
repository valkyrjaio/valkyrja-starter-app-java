/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.cli.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.cli.data.AppCliRoutingData;
import io.valkyrja.cli.routing.enum_.ArgumentMode;
import io.valkyrja.cli.routing.enum_.ArgumentValueMode;
import io.valkyrja.cli.routing.enum_.OptionMode;
import io.valkyrja.cli.routing.enum_.OptionValueMode;
import org.junit.jupiter.api.Test;

/** Test the sindri-generated {@link AppCliRoutingData}. */
final class AppCliRoutingDataTest {

    @Test
    void generatesTestCommandRoute() {
        var routes = new AppCliRoutingData().routes();

        // The test command plus the fourteen routing permutations.
        assertEquals(15, routes.size());
        assertTrue(routes.containsKey("test"));
        routes.values().forEach(route -> assertNotNull(route.get()));
    }

    /**
     * Every argument and option permutation survives code generation with the modes and metadata it
     * was declared with, so the cached commands bind input exactly as the router does.
     */
    @Test
    void generatesEveryArgumentAndOptionPermutation() {
        var routes = new AppCliRoutingData().routes();

        // Arguments keep their mode and value mode.
        var required = routes.get("permutations:argument-required").get().getArgument("value");
        assertEquals(ArgumentMode.REQUIRED, required.getMode());
        assertEquals(ArgumentValueMode.DEFAULT, required.getValueMode());

        var array = routes.get("permutations:argument-array").get().getArgument("values");
        assertEquals(ArgumentMode.OPTIONAL, array.getMode());
        assertEquals(ArgumentValueMode.ARRAY, array.getValueMode());

        var requiredArray =
                routes.get("permutations:argument-required-array").get().getArgument("values");
        assertEquals(ArgumentMode.REQUIRED, requiredArray.getMode());
        assertEquals(ArgumentValueMode.ARRAY, requiredArray.getValueMode());

        // Options keep their mode and value mode.
        var flag = routes.get("permutations:option-none").get().getOption("flag");
        assertEquals(OptionMode.OPTIONAL, flag.getMode());
        assertEquals(OptionValueMode.NONE, flag.getValueMode());

        var requiredOption = routes.get("permutations:option-required").get().getOption("value");
        assertEquals(OptionMode.REQUIRED, requiredOption.getMode());

        var arrayOption = routes.get("permutations:option-array").get().getOption("tag");
        assertEquals(OptionValueMode.ARRAY, arrayOption.getValueMode());

        // Short names, valid values and default values survive generation.
        assertEquals(
                java.util.List.of("m"),
                routes.get("permutations:option-short").get().getOption("marker").getShortNames());
        assertEquals(
                java.util.List.of("json", "xml"),
                routes.get("permutations:option-valid-values")
                        .get()
                        .getOption("format")
                        .getValidValues());
        assertEquals(
                "fallback",
                routes.get("permutations:option-default-value")
                        .get()
                        .getOption("value")
                        .getDefaultValue());

        // A command may declare arguments and options together.
        var mixed = routes.get("permutations:mixed").get();
        assertTrue(mixed.hasArgument("name"));
        assertTrue(mixed.hasOption("tag"));
    }
}
