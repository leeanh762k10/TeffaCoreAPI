package org.leeanh.TeffaCoreAPI.core.database;

import org.leeanh.TeffaCoreAPI.api.database.DatabaseService;
import org.leeanh.TeffaCoreAPI.core.database.driver.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseServiceImpl implements DatabaseService {

    private final DatabaseManager databaseManager;

    public DatabaseServiceImpl(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void initialize(){
        try {
            databaseManager.initialize();
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Failed to initialize database.",
                    exception
            );
        }
    }

    @Override
    public void shutdown() {
        try {
            databaseManager.shutdown();
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Failed to shutdown database.",
                    exception
            );
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return databaseManager.getConnection();
    }

    @Override
    public boolean isInitialized() {
        return databaseManager.isConnected();
    }
}
