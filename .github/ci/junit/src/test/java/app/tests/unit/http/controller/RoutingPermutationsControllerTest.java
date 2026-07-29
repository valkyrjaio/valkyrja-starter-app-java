/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.http.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import app.http.controller.RoutingPermutationsController;
import io.valkyrja.http.message.request.contract.ServerRequestContract;
import io.valkyrja.http.message.response.contract.TextResponseContract;
import io.valkyrja.http.message.response.factory.contract.ResponseFactoryContract;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** Test the routing permutations controller. */
final class RoutingPermutationsControllerTest {

    private final RoutingPermutationsController controller =
            new RoutingPermutationsController(
                    mock(ServerRequestContract.class), mock(ResponseFactoryContract.class));

    static Stream<Arguments> parameterRoutes() {
        return Stream.of(
                Arguments.of(
                        "num",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.num("42"),
                        "42"),
                Arguments.of(
                        "id",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.id("7"),
                        "7"),
                Arguments.of(
                        "slug",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.slug("my-slug-1"),
                        "my-slug-1"),
                Arguments.of(
                        "alpha",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.alpha("abc"),
                        "abc"),
                Arguments.of(
                        "alphaLowercase",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.alphaLowercase("abc"),
                        "abc"),
                Arguments.of(
                        "alphaUppercase",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.alphaUppercase("ABC"),
                        "ABC"),
                Arguments.of(
                        "alphaNum",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.alphaNum("abc123"),
                        "abc123"),
                Arguments.of(
                        "alphaNumUnderscore",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.alphaNumUnderscore("abc_123"),
                        "abc_123"),
                Arguments.of(
                        "any",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.any("anything-1.x"),
                        "anything-1.x"),
                Arguments.of(
                        "uuid",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.uuid("66a39476-b630-4b95-8bfb-355f3d4843c5"),
                        "66a39476-b630-4b95-8bfb-355f3d4843c5"),
                Arguments.of(
                        "ulid",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.ulid("01KYGBV64MKWPK63CC1QH0VGF7"),
                        "01KYGBV64MKWPK63CC1QH0VGF7"),
                Arguments.of(
                        "vlid",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.vlid("01KYGBV64MKWPK63CC1QH0VGF7"),
                        "01KYGBV64MKWPK63CC1QH0VGF7"),
                Arguments.of(
                        "optional present",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.optional("here"),
                        "here"),
                Arguments.of(
                        "optional absent",
                        (Function<RoutingPermutationsController, TextResponseContract>)
                                c -> c.optional(""),
                        "absent"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("parameterRoutes")
    void parameterRouteEchoesItsBoundValue(
            String label,
            Function<RoutingPermutationsController, TextResponseContract> action,
            String expected) {
        assertEquals(expected, body(action.apply(controller)));
    }

    @Test
    void multiCombinesBothParameters() {
        assertEquals("12-two", body(controller.multi("12", "two")));
    }

    @Test
    void parameterlessRoutesReturnTheirText() {
        assertEquals("non-capture", body(controller.nonCapture()));
        assertEquals("static", body(controller.staticRoute()));
        assertEquals("post", body(controller.post()));
        assertEquals("any-method", body(controller.anyMethod()));
    }

    private String body(TextResponseContract response) {
        return response.getBody().getContents();
    }
}
