package org.leeanh.TeffaCoreAPI.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.leeanh.TeffaCoreAPI.core.TeffaCorePlugin;

public class LeaveListener implements Listener {

    private final TeffaCorePlugin plugin;

    public LeaveListener(TeffaCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        plugin.getProfileManager()
                .unloadProfile(
                        event.getPlayer().getUniqueId()
                );

        plugin.getTeffaClientSessionManager()
                .removeSession(
                        event.getPlayer()
                );

        plugin.getLogger().info(
                "Profile removed for "
                        + event.getPlayer().getName()
        );
    }
}