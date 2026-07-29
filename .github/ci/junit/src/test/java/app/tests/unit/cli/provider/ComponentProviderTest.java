/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.tests.unit.cli.provider;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import app.cli.provider.ComponentProvider;
import io.valkyrja.application.kernel.contract.ApplicationContract;
import io.valkyrja.container.data.contract.ContainerDataContract;
import io.valkyrja.container.manager.Container;
import org.junit.jupiter.api.Test;

/** Test the {@link ComponentProvider}. */
final class ComponentProviderTest {

    private final ComponentProvider provider = new ComponentProvider();
    private final ApplicationContract app = mock(ApplicationContract.class);

    @Test
    void exposesAllProviderLists() {
        assertNotNull(provider.getComponentProviders(app));
        assertNotNull(provider.getContainerProviders(app));
        assertNotNull(provider.getEventProviders(app));
        assertNotNull(provider.getCliProviders(app));
        assertNotNull(provider.getHttpProviders(app));
        assertNotNull(provider.getGrpcProviders(app));
    }

    @Test
    void publishSkipsInDebugMode() {
        when(app.getDebugMode()).thenReturn(true);

        ComponentProvider.publish(app);
    }

    @Test
    void publishBindsContainerDataWhenNotDebug() {
        var container = new Container();
        when(app.getDebugMode()).thenReturn(false);
        when(app.getContainer()).thenReturn(container);

        ComponentProvider.publish(app);

        assertNotNull(container.getSingleton(ContainerDataContract.class));
    }
}
