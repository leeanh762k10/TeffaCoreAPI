package org.leeanh.TeffaCoreAPI.core.database.driver;

import org.leeanh.TeffaCoreAPI.core.database.DatabaseType;
import org.leeanh.TeffaCoreAPI.core.database.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseManager {

    private final DatabaseConfig config;
    private DatabaseDriver driver;

    public DatabaseManager(DatabaseConfig config) {
        this.config = config;
    }

    public void initialize() {
        if (config.type() == DatabaseType.MYSQL) {
            driver = new MySQLDriver();
        } else {
            driver = new SQLiteDriver();
        }

        driver.connect();
    }

    public void shutdown() {
        if (driver != null) {
            driver.disconnect();
        }
    }

    public Connection getConnection() throws SQLException {
        if (driver != null || !driver.isConnected()) {
            throw new SQLException("Driver is not connected");
        }

        return driver.getConnection();
    }

    public boolean isConnected() {
        return driver != null && driver.isConnected();
    }
}
