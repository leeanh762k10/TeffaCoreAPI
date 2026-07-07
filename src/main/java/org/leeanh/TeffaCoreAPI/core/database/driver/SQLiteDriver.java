package org.leeanh.TeffaCoreAPI.core.database.driver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.leeanh.TeffaCoreAPI.core.database.config.DatabaseConfig;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public final class SQLiteDriver implements DatabaseDriver {

    private final File dataFolder;
    private final DatabaseConfig config;

    private HikariDataSource dataSource;

    public SQLiteDriver(File dataFolder, DatabaseConfig config) {
        this.dataFolder = dataFolder;
        this.config = config;
    }

    @Override
    public void connect() throws SQLException {
        File databaseFile = new File
                (dataFolder, config.sqliteFile());

        File parentFolder = databaseFile.getParentFile();

        if(parentFolder != null && !parentFolder.exists()) {
            parentFolder.mkdirs();
        }

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setPoolName("TeffaCoreAPI-SQLite");
        hikariConfig.setJdbcUrl("jdbc:sqlite:" + databaseFile.getAbsolutePath());
        hikariConfig.setMaximumPoolSize(config.maximumPoolSize());
        hikariConfig.setMinimumIdle(config.minimumIdle());
        hikariConfig.setConnectionTimeout(config.connectionTimeout());

        dataSource = new HikariDataSource(hikariConfig);

        try (Connection connection = dataSource.getConnection()) {
            //test connection
        }
    }

    @Override
    public void disconnect() {
        if(dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            throw new UnsupportedOperationException("SQLite connection is not implemented yet.");
        }

        return dataSource.getConnection();
    }

    @Override
    public boolean isConnected() {
        return dataSource != null && !dataSource.isClosed();
    }

}
