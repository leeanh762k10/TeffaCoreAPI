package org.leeanh.TeffaCoreAPI.storage;

import org.leeanh.TeffaCoreAPI.profile.PlayerProfile;

import java.util.UUID;

public interface ProfileStorage {

    PlayerProfile load(UUID uuid);

    PlayerProfile findByName( String playerName );

    void save(PlayerProfile profile);
}