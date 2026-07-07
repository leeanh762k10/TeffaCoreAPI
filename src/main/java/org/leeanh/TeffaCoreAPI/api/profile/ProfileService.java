package org.leeanh.TeffaCoreAPI.api.profile;

import java.util.UUID;

public interface ProfileService {

    PlayerProfile getProfile(UUID uuid);

    PlayerProfile getProfile(String playerName);

    PlayerProfile findProfile(String playerName);

    boolean isLoaded(UUID uuid);
}