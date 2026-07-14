package org.leeanh.TeffaCoreAPI.core.storage;

import org.leeanh.TeffaCoreAPI.api.database.DatabaseService;
import org.leeanh.TeffaCoreAPI.api.profile.PlayerProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public final class SqlProfileStorage implements ProfileStorage {

    private final DatabaseService databaseService;

    public SqlProfileStorage(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public PlayerProfile load(UUID uuid) {
        String sql = """
                SELECT uuid, last_known_name, first_join, last_join
                FROM teffa_profiles
                WHERE uuid = ?
                """;

        try (
                Connection connection = databaseService.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, uuid.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                return mapProfile(resultSet);
            }

        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Failed to load profile: " + uuid,
                    exception
            );
        }
    }

    @Override
    public PlayerProfile findByName(String playerName) {
        String sql = """
                SELECT uuid, last_known_name, first_join, last_join
                FROM teffa_profiles
                WHERE LOWER(last_known_name) = LOWER(?)
                LIMIT 1
                """;

        try (
                Connection connection = databaseService.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, playerName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                return mapProfile(resultSet);
            }

        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Failed to find profile by name: " + playerName,
                    exception
            );
        }
    }

    @Override
    public void save(PlayerProfile profile) {
        String sql = """
                INSERT INTO teffa_profiles (
                    uuid,
                    last_known_name,
                    first_join,
                    last_join
                )
                VALUES (?, ?, ?, ?)
                ON CONFLICT(uuid) DO UPDATE SET
                    last_known_name = excluded.last_known_name,
                    first_join = excluded.first_join,
                    last_join = excluded.last_join
                """;

        try (
                Connection connection = databaseService.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(
                    1,
                    profile.getUuid().toString()
            );

            statement.setString(
                    2,
                    profile.getLastKnownName()
            );

            statement.setLong(
                    3,
                    profile.getFirstJoin()
            );

            statement.setLong(
                    4,
                    profile.getLastJoin()
            );

            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Failed to save profile: " + profile.getUuid(),
                    exception
            );
        }
    }

    private PlayerProfile mapProfile(
            ResultSet resultSet
    ) throws SQLException {

        return new PlayerProfile(
                UUID.fromString(
                        resultSet.getString("uuid")
                ),
                resultSet.getString(
                        "last_known_name"
                ),
                resultSet.getLong(
                        "first_join"
                ),
                resultSet.getLong(
                        "last_join"
                )
        );
    }
}