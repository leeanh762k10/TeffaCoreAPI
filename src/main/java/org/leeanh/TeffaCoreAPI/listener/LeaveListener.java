package org.leeanh.TeffaCoreAPI.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.leeanh.TeffaCoreAPI.api.profile.PlayerProfile;
import org.leeanh.TeffaCoreAPI.core.TeffaCorePlugin;

import java.util.UUID;

public class LeaveListener implements Listener {

    private final TeffaCorePlugin plugin;

    public LeaveListener(TeffaCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        PlayerProfile profile =
                plugin.getProfileManager().getProfile(uuid);

        if (profile == null) {
            return;
        }

        profile.setLastJoin(
                System.currentTimeMillis()
        );

        plugin.getProfileManager().unloadProfile(uuid);
    }
}