/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.cli.data;

import io.valkyrja.event.data.contract.EventDataContract;
import io.valkyrja.event.data.contract.ListenerContract;

import java.util.Map;

public record AppEventData() implements EventDataContract {

    @Override
    public Map<Class<?>, Map<String, String>> events() {
        return Map.of();
    }

    @Override
    public Map<String, ListenerContract> listeners() {
        return Map.of();
    }
}
