package org.leeanh.TeffaCoreAPI.client;

import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeffaClientSessionManager {

    private final Map<UUID, TeffaClientSession> sessions =
            new ConcurrentHashMap<>();

    public TeffaClientSession createPendingSession(Player player) {

        String sessionId =
                UUID.randomUUID().toString();

        String serverNonce =
                UUID.randomUUID().toString();

        Instant expiresAt =
                Instant.now().plus(Duration.ofMinutes(10));

        TeffaClientSession session =
                new TeffaClientSession(
                        player.getUniqueId(),
                        player.getName(),
                        sessionId,
                        serverNonce,
                        expiresAt
                );

        sessions.put(
                player.getUniqueId(),
                session
        );

        return session;
    }

    public Optional<TeffaClientSession> getSession(Player player) {

        return Optional.ofNullable(
                sessions.get(player.getUniqueId())
        );
    }

    public boolean isVerified(Player player) {

        return getSession(player)
                .map(TeffaClientSession::isVerified)
                .orElse(false);
    }

    public void removeSession(Player player) {

        sessions.remove(
                player.getUniqueId()
        );
    }

    public void clearExpiredSessions() {

        sessions.entrySet()
                .removeIf(entry ->
                        entry.getValue().isExpired()
                );
    }
}