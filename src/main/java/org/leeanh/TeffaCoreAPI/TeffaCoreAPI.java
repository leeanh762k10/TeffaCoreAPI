/*
 * Copyright (c) 2026 Nguyễn Lê Anh.
 *
 * This file is part of TeffaCoreAPI.
 * All rights reserved.
 */
package org.leeanh.TeffaCoreAPI;

import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.leeanh.TeffaCoreAPI.api.ProfileService;
import org.leeanh.TeffaCoreAPI.api.ProfileServiceImpl;
import org.leeanh.TeffaCoreAPI.client.TeffaClientBridge;
import org.leeanh.TeffaCoreAPI.client.TeffaClientSessionManager;
import org.leeanh.TeffaCoreAPI.diagnostic.DiagnosticService;
import org.leeanh.TeffaCoreAPI.diagnostic.DiagnosticServiceImpl;
import org.leeanh.TeffaCoreAPI.listener.JoinListener;
import org.leeanh.TeffaCoreAPI.listener.LeaveListener;
import org.leeanh.TeffaCoreAPI.permission.PermissionService;
import org.leeanh.TeffaCoreAPI.permission.PermissionServiceImpl;
import org.leeanh.TeffaCoreAPI.profile.PlayerProfile;
import org.leeanh.TeffaCoreAPI.profile.ProfileManager;
import org.leeanh.TeffaCoreAPI.storage.JsonProfileStorage;
import org.leeanh.TeffaCoreAPI.storage.ProfileStorage;
import org.leeanh.TeffaCoreAPI.command.TeffaCommand;

import java.io.File;

public final class TeffaCoreAPI extends JavaPlugin {

    private ProfileManager profileManager;
    private ProfileStorage profileStorage;

    private LuckPerms luckPerms;
    private PermissionService permissionService;

    private TeffaClientSessionManager teffaClientSessionManager;
    private TeffaClientBridge teffaClientBridge;

    @Override
    public void onEnable() {

        setupProfileSystem();

        setupDiagnosticSystem();

        setupLuckPerms();

        setupTeffaClientBridge();

        registerListeners();

        registerCommands();

        getLogger().info(
                "TeffaCoreAPI enabled!"
        );
    }

    private void setupProfileSystem() {

        profileStorage =
                new JsonProfileStorage(
                        new File(
                                getDataFolder(),
                                "playerdata"
                        )
                );

        profileManager =
                new ProfileManager(
                        profileStorage
                );

        ProfileService profileService =
                new ProfileServiceImpl(
                        profileManager
                );

        getServer()
                .getServicesManager()
                .register(
                        ProfileService.class,
                        profileService,
                        this,
                        ServicePriority.Normal
                );

        getLogger().info(
                "ProfileService registered!"
        );
    }

    private void setupDiagnosticSystem() {

        DiagnosticService diagnosticService =
                new DiagnosticServiceImpl();

        getServer()
                .getServicesManager()
                .register(
                        DiagnosticService.class,
                        diagnosticService,
                        this,
                        ServicePriority.Normal
                );

        getLogger().info(
                "DiagnosticService registered!"
        );
    }

    private void setupLuckPerms() {

        RegisteredServiceProvider<LuckPerms> provider =
                getServer()
                        .getServicesManager()
                        .getRegistration(
                                LuckPerms.class
                        );

        if (provider == null) {

            getLogger().warning(
                    "LuckPerms not found!"
            );

            return;
        }

        luckPerms =
                provider.getProvider();

        permissionService =
                new PermissionServiceImpl(
                        luckPerms
                );

        getServer()
                .getServicesManager()
                .register(
                        PermissionService.class,
                        permissionService,
                        this,
                        ServicePriority.Normal
                );

        getLogger().info(
                "Hooked into LuckPerms!"
        );
    }

    private void setupTeffaClientBridge() {

        teffaClientSessionManager =
                new TeffaClientSessionManager();

        teffaClientBridge =
                new TeffaClientBridge(
                        this,
                        teffaClientSessionManager
                );

        teffaClientBridge.registerChannels();

        getLogger().info(
                "TeffaClientBridge registered!"
        );
    }

    private void registerListeners() {

        getServer()
                .getPluginManager()
                .registerEvents(
                        new JoinListener(
                                this
                        ),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new LeaveListener(
                                this
                        ),
                        this
                );
    }

    private void registerCommands() {

        if (getCommand("teffa") != null) {

            getCommand("teffa").setExecutor(
                    new TeffaCommand(
                            this
                    )
            );

            getLogger().info(
                    "Teffa command registered!"
            );

        } else {

            getLogger().warning(
                    "Command /teffa is missing in plugin.yml!"
            );
        }
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public PermissionService getPermissionService() {
        return permissionService;
    }

    public TeffaClientSessionManager getTeffaClientSessionManager() {
        return teffaClientSessionManager;
    }

    public TeffaClientBridge getTeffaClientBridge() {
        return teffaClientBridge;
    }

    @Override
    public void onDisable() {

        if (teffaClientBridge != null) {
            teffaClientBridge.unregisterChannels();
        }

        if (profileManager != null && profileStorage != null) {

            for (PlayerProfile profile :
                    profileManager.getProfiles()) {

                profileStorage.save(profile);
            }

            getLogger().info(
                    "Saved all profiles."
            );
        }

        getServer()
                .getServicesManager()
                .unregisterAll(
                        this
                );

        getLogger().info(
                "TeffaCoreAPI disabled!"
        );
    }
}