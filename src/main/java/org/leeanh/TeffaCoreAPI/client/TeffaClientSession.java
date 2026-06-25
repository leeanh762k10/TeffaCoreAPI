package org.leeanh.TeffaCoreAPI.client;

import java.time.Instant;
import java.util.UUID;

public class TeffaClientSession {

    private final UUID playerUuid;
    private final String playerName;

    private final String sessionId;
    private final String serverNonce;

    private TeffaClientStatus status;

    private String clientVersion;
    private int protocolVersion;

    private final Instant createdAt;
    private Instant expiresAt;

    private long lastSequence;

    public TeffaClientSession(
            UUID playerUuid,
            String playerName,
            String sessionId,
            String serverNonce,
            Instant expiresAt
    ) {
        this.playerUuid = playerUuid;
        this.playerName = playerName;
        this.sessionId = sessionId;
        this.serverNonce = serverNonce;
        this.status = TeffaClientStatus.PENDING_HANDSHAKE;
        this.createdAt = Instant.now();
        this.expiresAt = expiresAt;
        this.lastSequence = 0;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getServerNonce() {
        return serverNonce;
    }

    public TeffaClientStatus getStatus() {
        return status;
    }

    public void setStatus(TeffaClientStatus status) {
        this.status = status;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public long getLastSequence() {
        return lastSequence;
    }

    public void setLastSequence(long lastSequence) {
        this.lastSequence = lastSequence;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean isVerified() {
        return status == TeffaClientStatus.VERIFIED
                && !isExpired();
    }
}