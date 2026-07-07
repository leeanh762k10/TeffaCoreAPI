package org.leeanh.TeffaCoreAPI.core.database.driver;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseDriver {

    void connect();

    void disconnect();

    Connection getConnection() throws SQLException;

    boolean isConnected();
}
