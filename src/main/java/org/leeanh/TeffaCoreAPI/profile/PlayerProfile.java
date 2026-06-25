package org.leeanh.TeffaCoreAPI.profile;

import java.util.UUID;

public class PlayerProfile {

    private UUID uuid;

    private String lastKnownName;

    private long firstJoin;

    private long lastJoin;

    public PlayerProfile() {
    }

    public PlayerProfile(
            UUID uuid,
            String lastKnownName,
            long firstJoin,
            long lastJoin
    ) {

        this.uuid = uuid;
        this.lastKnownName = lastKnownName;

        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLastKnownName() {
        return lastKnownName;
    }

    public void setLastKnownName(
            String lastKnownName
    ) {
        this.lastKnownName = lastKnownName;
    }

    public long getFirstJoin() {
        return firstJoin;
    }

    public void setFirstJoin(
            long firstJoin
    ) {
        this.firstJoin = firstJoin;
    }

    public long getLastJoin() {
        return lastJoin;
    }

    public void setLastJoin(
            long lastJoin
    ) {
        this.lastJoin = lastJoin;
    }
}