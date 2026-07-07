package org.leeanh.TeffaCoreAPI.core.database.driver;

import java.sql.Connection;
import java.sql.SQLException;

public final class MySQLDriver implements DatabaseDriver {

    private boolean connected;

    @Override
    public void connect() {
        connected = true;
    }

    @Override
    public void disconnect() {
        connected = false;
    }

    @Override
    public Connection getConnection() throws SQLException {
        throw new UnsupportedOperationException("MySQL connection is not implemented yet.");
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

}
