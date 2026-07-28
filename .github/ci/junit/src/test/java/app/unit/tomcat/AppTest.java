/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.unit.tomcat;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Test the {@link app.tomcat.App} entry point.
 *
 * <p>The entry's {@code main} blocks on the Tomcat server loop, so it cannot run in-process; the live
 * request path is covered end to end by {@code app.functional.entry.TomcatAppTest}.
 */
final class AppTest {

    @Test
    void isInstantiable() {
        assertNotNull(new app.tomcat.App());
    }
}
