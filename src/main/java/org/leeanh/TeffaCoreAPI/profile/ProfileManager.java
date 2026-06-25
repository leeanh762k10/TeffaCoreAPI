package org.leeanh.TeffaCoreAPI.profile;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.leeanh.TeffaCoreAPI.storage.ProfileStorage;

public class ProfileManager {

    private final Map<UUID, PlayerProfile> profiles =
            new ConcurrentHashMap<>();
    public Collection<PlayerProfile> getProfiles() {
        return profiles.values();
    }

    private final ProfileStorage storage;

    public ProfileManager(ProfileStorage storage) {
        this.storage = storage;
    }

    public PlayerProfile findProfile(
            String playerName
    ) {

        PlayerProfile loaded =
                profiles.values()
                        .stream()
                        .filter(profile ->
                                profile.getLastKnownName()
                                        .equalsIgnoreCase(
                                                playerName
                                        ))
                        .findFirst()
                        .orElse(null);

        if (loaded != null) {
            return loaded;
        }

        return storage.findByName(
                playerName
        );
    }

    public PlayerProfile getProfile(UUID uuid) {

        return profiles.get(uuid);
    }

    public PlayerProfile loadProfile(
            UUID uuid,
            String playerName
    ) {

        PlayerProfile profile =
                storage.load(uuid);

        if (profile == null) {

            long now =
                    System.currentTimeMillis();

            profile =
                    new PlayerProfile(
                            uuid,
                            playerName,
                            now,
                            now
                    );
        }

        profile.setLastKnownName(
                playerName
        );

        profile.setLastJoin(
                System.currentTimeMillis()
        );

        profiles.put(uuid, profile);

        return profile;
    }

    public void unloadProfile(UUID uuid) {

        PlayerProfile profile =
                profiles.get(uuid);

        if (profile != null) {

            storage.save(profile);

            profiles.remove(uuid);
        }
    }

    public int getLoadedProfiles() {
        return profiles.size();
    }
}