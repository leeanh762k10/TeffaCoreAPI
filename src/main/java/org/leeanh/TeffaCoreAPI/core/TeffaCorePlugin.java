/*
 * Copyright (c) 2026 Nguyễn Lê Anh.
 *
 * This file is part of TeffaCoreAPI.
 * All rights reserved.
 */
package org.leeanh.TeffaCoreAPI.core;

import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.leeanh.TeffaCoreAPI.api.database.DatabaseService;
import org.leeanh.TeffaCoreAPI.api.profile.ProfileService;
import org.leeanh.TeffaCoreAPI.core.database.DatabaseServiceImpl;
import org.leeanh.TeffaCoreAPI.core.database.config.DatabaseConfig;
import org.leeanh.TeffaCoreAPI.core.database.driver.DatabaseManager;
import org.leeanh.TeffaCoreAPI.core.database.migration.MigrationManager;
import org.leeanh.TeffaCoreAPI.core.profile.ProfileServiceImpl;
import org.leeanh.TeffaCoreAPI.api.TeffaCoreAPI;
import org.leeanh.TeffaCoreAPI.client.TeffaClientBridge;
import org.leeanh.TeffaCoreAPI.client.TeffaClientSessionManager;
import org.leeanh.TeffaCoreAPI.api.diagnostic.DiagnosticService;
import org.leeanh.TeffaCoreAPI.core.diagnostic.DiagnosticServiceImpl;
import org.leeanh.TeffaCoreAPI.listener.JoinListener;
import org.leeanh.TeffaCoreAPI.listener.LeaveListener;
import org.leeanh.TeffaCoreAPI.api.permission.PermissionService;
import org.leeanh.TeffaCoreAPI.core.permission.PermissionServiceImpl;
import org.leeanh.TeffaCoreAPI.api.profile.PlayerProfile;
import org.leeanh.TeffaCoreAPI.core.profile.ProfileManager;
import org.leeanh.TeffaCoreAPI.core.storage.JsonProfileStorage;
import org.leeanh.TeffaCoreAPI.core.storage.ProfileStorage;
import org.leeanh.TeffaCoreAPI.command.TeffaCommand;

import java.io.File;

public final class TeffaCorePlugin extends JavaPlugin implements TeffaCoreAPI {

    private ProfileManager profileManager;
    private ProfileStorage profileStorage;
    private ProfileService profileService;
    private DiagnosticService diagnosticService;
    private DatabaseService databaseService;

    private LuckPerms luckPerms;
    private PermissionService permissionService;

    private TeffaClientSessionManager teffaClientSessionManager;
    private TeffaClientBridge teffaClientBridge;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        TeffaCoreProvider.register(this);

        setupDatabaseSystem();

        databaseService.initialize();

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

        profileService =
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

        diagnosticService =
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

    private void setupDatabaseSystem() {

        DatabaseConfig databaseConfig =
                new DatabaseConfig(getConfig());

        DatabaseManager databaseManager =
                new DatabaseManager(
                        databaseConfig,
                        getDataFolder()
                );

        databaseService =
                new DatabaseServiceImpl(databaseManager);

        databaseService.initialize();

        MigrationManager migrationManager =
                new MigrationManager(databaseService);

        migrationManager.migrate();

        getServer()
                .getServicesManager()
                .register(
                        DatabaseService.class,
                        databaseService,
                        this,
                        ServicePriority.Normal
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

    @Override
    public ProfileService profileService() {
        return profileService;
    }

    @Override
    public PermissionService permissionService() {
        return permissionService;
    }

    @Override
    public DiagnosticService diagnosticService() {
        return diagnosticService;
    }

    @Override
    public DatabaseService databaseService() {
        return databaseService;
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

            for (PlayerProfile profile : profileManager.getProfiles()) {
                profileStorage.save(profile);
            }

            getLogger().info("Saved all profiles.");
        }

        getServer()
                .getServicesManager()
                .unregisterAll(this);

        if (databaseService != null) {
            databaseService.shutdown();
        }

        TeffaCoreProvider.unregister();

        getLogger().info("TeffaCoreAPI disabled!");
    }
}