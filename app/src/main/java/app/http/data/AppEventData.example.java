/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.http.data;

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
