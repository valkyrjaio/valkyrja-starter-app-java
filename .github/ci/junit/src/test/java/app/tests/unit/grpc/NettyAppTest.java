/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.grpc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Test the {@link app.grpc.NettyApp} entry point.
 *
 * <p>The entry's {@code main} blocks on the Netty gRPC server loop, so it cannot run in-process;
 * the server it builds is covered end to end by {@code
 * app.tests.functional.entry.NettyGrpcAppTest}.
 */
final class NettyAppTest {

    @Test
    void isInstantiable() {
        assertNotNull(new app.grpc.NettyApp());
    }
}
