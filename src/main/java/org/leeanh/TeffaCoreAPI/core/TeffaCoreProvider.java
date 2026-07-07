package org.leeanh.TeffaCoreAPI.core;

import org.leeanh.TeffaCoreAPI.api.TeffaCoreAPI;

public final class TeffaCoreProvider {

    private static TeffaCoreAPI api;

    private TeffaCoreProvider() {
    }

    public static void register(TeffaCoreAPI instance) {
        api = instance;
    }

    public static void unregister() {
        api = null;
    }

    public static TeffaCoreAPI get() {
        if (api == null) {
            throw new IllegalStateException("TeffaCoreAPI is not initialized.");
        }

        return api;
    }
}