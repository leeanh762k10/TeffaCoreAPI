package org.leeanh.TeffaCoreAPI.api;

import org.leeanh.TeffaCoreAPI.profile.PlayerProfile;
import org.leeanh.TeffaCoreAPI.profile.ProfileManager;

import java.util.UUID;

public class ProfileServiceImpl
        implements ProfileService {

    private final ProfileManager profileManager;

    @Override
    public PlayerProfile findProfile(
            String playerName
    ) {

        return profileManager.findProfile(
                playerName
        );
    }

    @Override
    public PlayerProfile getProfile(UUID uuid) {

        return profileManager.getProfile(uuid);
    }
    public ProfileServiceImpl(
            ProfileManager profileManager
    ) {
        this.profileManager = profileManager;
    }

    @Override
    public PlayerProfile getProfile(String playerName) {

        return profileManager.findProfile(
                playerName
        );
    }

    @Override
    public boolean isLoaded(UUID uuid) {

        return profileManager.getProfile(uuid) != null;
    }
}