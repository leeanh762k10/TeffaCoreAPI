package org.leeanh.TeffaCoreAPI.core.database;

import org.leeanh.TeffaCoreAPI.api.database.DatabaseService;

import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseServiceImpl implements DatabaseService {

    private boolean initialized;

    @Override
    public void initialize() {
        initialized = true;
    }

    @Override
    public void shutdown() {
        initialized = false;
    }

    @Override
    public Connection getConnection() throws SQLException {
        throw new UnsupportedOperationException("Database connection is not implemented yet.");
    }

    @Override
    public boolean isInitialized() {
        return false;
    }
}
