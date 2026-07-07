package org.leeanh.TeffaCoreAPI.core.permisson;

import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;
import net.luckperms.api.model.user.User;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.query.QueryOptions;
import org.leeanh.TeffaCoreAPI.api.permisson.PermissionService;

public class PermissionServiceImpl
        implements PermissionService {

    private final LuckPerms luckPerms;

    public PermissionServiceImpl(
            LuckPerms luckPerms
    ) {

        this.luckPerms = luckPerms;
    }

    @Override
    public String getPrefix(Player player) {

        User user =
                luckPerms
                        .getPlayerAdapter(Player.class)
                        .getUser(player);

        QueryOptions queryOptions =
                luckPerms
                        .getContextManager()
                        .getQueryOptions(player);

        CachedMetaData metaData =
                user.getCachedData()
                        .getMetaData(queryOptions);

        String prefix =
                metaData.getPrefix();

        return prefix == null
                ? ""
                : prefix;
    }

    @Override
    public String getSuffix(Player player) {

        User user =
                luckPerms
                        .getPlayerAdapter(Player.class)
                        .getUser(player);

        QueryOptions queryOptions =
                luckPerms
                        .getContextManager()
                        .getQueryOptions(player);

        CachedMetaData metaData =
                user.getCachedData()
                        .getMetaData(queryOptions);

        String suffix =
                metaData.getSuffix();

        return suffix == null
                ? ""
                : suffix;
    }

    @Override
    public String getPrimaryGroup(
            Player player
    ) {

        User user =
                luckPerms
                        .getPlayerAdapter(Player.class)
                        .getUser(player);

        return user.getPrimaryGroup();
    }
}