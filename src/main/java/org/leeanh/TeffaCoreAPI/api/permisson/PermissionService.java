package org.leeanh.TeffaCoreAPI.api.permisson;

import org.bukkit.entity.Player;

public interface PermissionService {

    String getPrefix(Player player);

    String getSuffix(Player player);

    String getPrimaryGroup(Player player);

}