package org.leeanh.TeffaCoreAPI.core.database.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.leeanh.TeffaCoreAPI.core.database.DatabaseType;

public final class DatabaseConfig {

    private final DatabaseType type;

    private final String sqliteFile;

    private final String mysqlHost;
    private final int mysqlPort;
    private final String mysqlDatabase;
    private final String mysqlUsername;
    private final String mysqlPassword;
    private final String mysqlParameters;

    private final int maximumPoolSize;
    private final int minimumIdle;
    private final long connectionTimeout;

    public DatabaseConfig(FileConfiguration config) {
        String rawType = config.getString("database.type", "sqlite");

        //DATABASE TYPE
        if(rawType.equalsIgnoreCase("mysql")) {
            this.type =  DatabaseType.MYSQL;
        } else {
            this.type = DatabaseType.SQLITE;
        }

        //SQLITE
        this.sqliteFile = config.getString(
                "database.sqlite.file",
                "database/teffa.db"
        );

        //MYSQL
        this.mysqlHost = config.getString("database.mysql.host", "localhost");
        this.mysqlPort = config.getInt("database.mysql.port", 3306);
        this.mysqlDatabase = config.getString("database.mysql.database", "teffa");
        this.mysqlUsername = config.getString("database.mysql.username", "root");
        this.mysqlPassword = config.getString("database.mysql.password", "");
        this.mysqlParameters = config.getString("database.mysql.parameters", "?useSSL=false&serverTimezone=UTC");

        //CONNECTION POOL
        this.maximumPoolSize = config.getInt("database.pool.maximumPoolSize", 10);
        this.minimumIdle = config.getInt("database.pool.minimumIdle", 2);
        this.connectionTimeout = config.getInt("database.pool.connectionTimeout", 30000);
    }

    public DatabaseType type() {
        return type;
    }

    public String sqliteFile() {
        return sqliteFile;
    }

    public String mysqlHost() {
        return mysqlHost;
    }

    public int mysqlPort() {
        return mysqlPort;
    }

    public String mysqlDatabase() {
        return mysqlDatabase;
    }

    public String mysqlUsername() {
        return mysqlUsername;
    }

    public String mysqlPassword() {
        return mysqlPassword;
    }

    public String mysqlParameters() {
        return mysqlParameters;
    }

    public int maximumPoolSize() {
        return maximumPoolSize;
    }

    public int minimumIdle() {
        return minimumIdle;
    }

    public long connectionTimeout() {
        return connectionTimeout;
    }
}
