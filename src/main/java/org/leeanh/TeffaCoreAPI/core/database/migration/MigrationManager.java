package org.leeanh.TeffaCoreAPI.core.database.migration;

import org.leeanh.TeffaCoreAPI.api.database.DatabaseService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class MigrationManager {

    private final DatabaseService databaseService;

    public MigrationManager(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void migrate() {
        try (Connection connection = databaseService.getConnection();
             Statement statement = connection.createStatement()) {

            createProfilesTable(statement);

        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Failed to run database migrations.",
                    exception
            );
        }
    }

    private void createProfilesTable(Statement statement) throws SQLException {
        statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS teffa_profiles (
                    uuid TEXT PRIMARY KEY,
                    last_known_name TEXT NOT NULL,
                    first_join BIGINT NOT NULL,
                    last_join BIGINT NOT NULL
                )
                """);
    }
}
