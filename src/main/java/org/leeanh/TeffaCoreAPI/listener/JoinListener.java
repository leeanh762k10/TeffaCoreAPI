package org.leeanh.TeffaCoreAPI.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.leeanh.TeffaCoreAPI.core.TeffaCorePlugin;
import org.leeanh.TeffaCoreAPI.api.profile.PlayerProfile;

public class JoinListener implements Listener {

    private final TeffaCorePlugin plugin;

    public JoinListener(TeffaCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        PlayerProfile profile =
                plugin.getProfileManager()
                        .loadProfile(
                                event.getPlayer().getUniqueId(),
                                event.getPlayer().getName()
                        );

        plugin.getLogger().info(
                "Loaded Profiles: "
                        + plugin.getProfileManager()
                        .getLoadedProfiles()
        );

        Bukkit.getScheduler().runTaskLater(
                plugin,
                () -> plugin.getTeffaClientBridge()
                        .sendActive(event.getPlayer()),
                20L
        );
    }
}