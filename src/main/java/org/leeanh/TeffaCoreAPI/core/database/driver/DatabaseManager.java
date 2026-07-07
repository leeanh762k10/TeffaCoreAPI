package org.leeanh.TeffaCoreAPI.core.database.driver;

import org.leeanh.TeffaCoreAPI.core.database.DatabaseType;
import org.leeanh.TeffaCoreAPI.core.database.config.DatabaseConfig;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseManager {

    private final DatabaseConfig config;
    private final File dataFolder;

    private DatabaseDriver driver;

    public DatabaseManager(DatabaseConfig config, File dataFolder) {
        this.config = config;
        this.dataFolder = dataFolder;
    }

    public void initialize() throws SQLException {
        if (config.type() == DatabaseType.MYSQL) {
            driver = new MySQLDriver();
        } else {
            driver = new SQLiteDriver(dataFolder, config);
        }

        driver.connect();
    }

    public void shutdown() throws SQLException {
        if (driver != null) {
            driver.disconnect();
        }
    }

    public Connection getConnection() throws SQLException {
        if (driver == null || !driver.isConnected()) {
            throw new SQLException("Driver is not connected.");
        }

        return driver.getConnection();
    }

    public boolean isConnected() {
        return driver != null && driver.isConnected();
    }
}
