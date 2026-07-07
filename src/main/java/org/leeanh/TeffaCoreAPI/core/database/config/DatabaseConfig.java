package org.leeanh.TeffaCoreAPI.core.database.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.leeanh.TeffaCoreAPI.core.database.DatabaseType;

public final class DatabaseConfig {

    private final DatabaseType type;

    public DatabaseConfig(FileConfiguration config) {
        String rawType = config.getString("database.type", "sqlite");

        if(rawType.equalsIgnoreCase("mysql")) {
            this.type =  DatabaseType.MYSQL;
        } else {
            this.type = DatabaseType.SQLITE;
        }
    }

    public DatabaseType type() {
        return type;
    }
}
