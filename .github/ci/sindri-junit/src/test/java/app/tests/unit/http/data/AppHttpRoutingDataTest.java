/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.http.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.http.data.AppHttpRoutingData;
import io.valkyrja.http.routing.constant.Regex;
import org.junit.jupiter.api.Test;

/** Test the sindri-generated {@link AppHttpRoutingData}. */
final class AppHttpRoutingDataTest {

    @Test
    void generatesRoutingData() {
        var data = new AppHttpRoutingData();

        var routes = data.routes();
        // Eight routes on HomeController plus the eighteen routing permutations.
        assertEquals(26, routes.size());
        assertTrue(routes.containsKey("welcome"));
        routes.values().forEach(route -> assertNotNull(route.get()));

        var paths = data.paths();
        assertTrue(paths.containsKey("GET"));
        assertTrue(paths.containsKey("POST"));
        assertTrue(paths.containsKey("PUT"));
        assertTrue(paths.containsKey("HEAD"));

        // The routing permutations contribute dynamic paths and their regexes.
        assertFalse(data.dynamicPaths().isEmpty());
        assertFalse(data.regexes().isEmpty());
    }

    /**
     * Every routing permutation is generated with the exact regex the framework's processor
     * produces, so the cached routing table matches what the router builds at runtime.
     */
    @Test
    void generatesTheExpectedRegexForEveryPermutation() {
        var data = new AppHttpRoutingData();
        var regexes = data.regexes().get("GET");

        var expected = new java.util.LinkedHashMap<String, String>();
        expected.put(
                "permutations.num", anchored("permutations\\/num", "(?<value>" + Regex.NUM + ")"));
        expected.put(
                "permutations.id", anchored("permutations\\/id", "(?<value>" + Regex.ID + ")"));
        expected.put(
                "permutations.slug",
                anchored("permutations\\/slug", "(?<value>" + Regex.SLUG + ")"));
        expected.put(
                "permutations.alpha",
                anchored("permutations\\/alpha", "(?<value>" + Regex.ALPHA + ")"));
        expected.put(
                "permutations.any", anchored("permutations\\/any", "(?<value>" + Regex.ANY + ")"));
        expected.put(
                "permutations.uuid",
                anchored("permutations\\/uuid", "(?<value>" + Regex.UUID + ")"));
        expected.put(
                "permutations.ulid",
                anchored("permutations\\/ulid", "(?<value>" + Regex.ULID + ")"));
        expected.put(
                "permutations.vlid",
                anchored("permutations\\/vlid", "(?<value>" + Regex.VLID + ")"));

        expected.forEach(
                (name, regex) -> assertEquals(name, regexes.get(regex), "regex for " + name));

        // A non-capturing parameter produces a group without a name.
        assertEquals(
                "permutations.nonCapture",
                regexes.get(anchored("permutations\\/non-capture", "(?:" + Regex.ALPHA + ")")));
    }

    /** Build the regex the processor produces for a single-parameter route. */
    private static String anchored(String staticSegment, String parameterGroup) {
        return Regex.START + Regex.PATH + staticSegment + Regex.PATH + parameterGroup + Regex.END;
    }
}
