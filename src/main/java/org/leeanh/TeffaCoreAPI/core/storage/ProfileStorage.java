package org.leeanh.TeffaCoreAPI.core.storage;

import org.leeanh.TeffaCoreAPI.api.profile.PlayerProfile;

import java.util.UUID;

public interface ProfileStorage {

    PlayerProfile load(UUID uuid);

    PlayerProfile findByName( String playerName );

    void save(PlayerProfile profile);
}