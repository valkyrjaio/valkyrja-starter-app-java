/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.fixtures.entry;

import static org.junit.jupiter.api.Assertions.fail;

import app.http.Config;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Shared harness for the runtime end-to-end tests.
 *
 * <p>Reserves a free port, waits for a started server to accept connections, performs a real HTTP
 * request against it, and tears the server down robustly — so each runtime's test only has to say
 * how its own server starts and stops.
 */
public final class RuntimeServerFixture {

    private RuntimeServerFixture() {}

    /**
     * Copy the application's HTTP configuration onto a specific port.
     *
     * <p>Built here rather than as a {@code Config} constructor on purpose: Sindri reads the
     * configuration's no-argument constructor to extract the component providers, so moving that
     * body behind another constructor makes it generate empty {@code App*Data}. The configuration
     * stays exactly as the application ships it, and only the port is swapped.
     *
     * @param port the port to bind
     * @return the configuration bound to that port
     */
    public static Config configOnPort(int port) {
        Config config = new Config();

        return new Config(
                config.namespace(),
                config.dir(),
                config.version(),
                config.environment(),
                config.debugMode(),
                config.timezone(),
                config.key(),
                config.dataPath(),
                config.dataNamespace(),
                port,
                config.requestReceivedMiddleware(),
                config.routeMatchedMiddleware(),
                config.routeNotMatchedMiddleware(),
                config.routeDispatchedMiddleware(),
                config.throwableCaughtMiddleware(),
                config.sendingResponseMiddleware(),
                config.responseSentMiddleware(),
                config.providers(),
                config.callbacks());
    }

    /**
     * Copy the application's gRPC configuration onto a specific port.
     *
     * <p>Built here for the same reason as {@link #configOnPort(int)}: Sindri reads the
     * configuration's no-argument constructor to extract the component providers, so the shipped
     * configuration must not grow another constructor. Passing port {@code 0} lets the runtime pick
     * an ephemeral port, so concurrent runs never collide.
     *
     * @param port the port to bind
     * @return the gRPC configuration bound to that port
     */
    public static app.grpc.Config grpcConfigOnPort(int port) {
        app.grpc.Config config = new app.grpc.Config();

        return new app.grpc.Config(
                config.namespace(),
                config.dir(),
                config.version(),
                config.environment(),
                config.debugMode(),
                config.timezone(),
                config.key(),
                config.dataPath(),
                config.dataNamespace(),
                port,
                config.callReceivedMiddleware(),
                config.routeMatchedMiddleware(),
                config.routeNotMatchedMiddleware(),
                config.routeDispatchedMiddleware(),
                config.throwableCaughtMiddleware(),
                config.sendingResponseMiddleware(),
                config.responseSentMiddleware(),
                config.providers(),
                config.callbacks());
    }

    /**
     * Reserve a free localhost TCP port.
     *
     * <p>The socket is closed before the port is returned, so the server under test can bind it.
     * Binding a free port (rather than a fixed one) keeps concurrent runs from colliding.
     *
     * @return the reserved port
     */
    public static int freePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            return fail("Unable to reserve a free port.", e);
        }
    }

    /**
     * Determine whether a runtime is on the classpath.
     *
     * <p>Lets a runtime's end-to-end test skip cleanly wherever that runtime is absent instead of
     * failing.
     *
     * @param className a class the runtime provides
     * @return whether the runtime is available
     */
    public static boolean isAvailable(String className) {
        try {
            Class.forName(className);

            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Wait for a server to accept connections on the given port.
     *
     * @param port the port the server binds
     */
    public static void awaitPort(int port) {
        long deadline = System.nanoTime() + Duration.ofSeconds(20).toNanos();

        while (System.nanoTime() < deadline) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("127.0.0.1", port), 200);

                return;
            } catch (IOException e) {
                sleep();
            }
        }

        fail("The server never started listening on port " + port + ".");
    }

    /**
     * Perform a real HTTP GET against the running server.
     *
     * @param port the port the server binds
     * @param path the request path
     * @return the response body
     */
    public static String get(int port, String path) {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:" + port + path))
                        .timeout(Duration.ofSeconds(10))
                        .GET()
                        .build();

        try (HttpClient client =
                HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build()) {
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException e) {
            return fail("The request to " + path + " failed.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            return fail("The request to " + path + " was interrupted.", e);
        }
    }

    /**
     * Stop a server, never letting a teardown failure mask the test result.
     *
     * <p>A runtime that refuses to shut down cleanly must not leave the port bound for the next
     * test, so any failure is reported rather than thrown.
     *
     * @param stop the runtime's shutdown call
     */
    public static void stopQuietly(ThrowingRunnable stop) {
        try {
            stop.run();
        } catch (Exception e) {
            System.err.println("Failed to stop the server cleanly: " + e);
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            fail("Interrupted while waiting for the server to start.", e);
        }
    }

    /** A shutdown call that is allowed to throw. */
    @FunctionalInterface
    public interface ThrowingRunnable {

        void run() throws Exception;
    }
}
