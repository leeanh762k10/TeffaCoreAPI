package org.leeanh.TeffaCoreAPI.api.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseService {
    void initialize();
    void shutdown();
    Connection getConnection() throws SQLException;
    boolean isInitialized();
}
