package org.leeanh.TeffaCoreAPI.core.database.driver;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseDriver {

    void connect() throws SQLException;

    void disconnect() throws SQLException;

    Connection getConnection() throws SQLException;

    boolean isConnected();
}
