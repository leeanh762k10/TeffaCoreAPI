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
    public void initialize() {
        databaseManager.initialize();
    }

    @Override
    public void shutdown() {
        databaseManager.initialize();
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
