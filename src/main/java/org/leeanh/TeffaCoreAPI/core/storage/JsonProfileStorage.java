package org.leeanh.TeffaCoreAPI.core.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.leeanh.TeffaCoreAPI.api.profile.PlayerProfile;

import java.io.*;
import java.util.UUID;

public class JsonProfileStorage implements ProfileStorage {

    private final File dataFolder;

    private final Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    public JsonProfileStorage(File dataFolder) {

        this.dataFolder = dataFolder;

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    @Override
    public PlayerProfile load(UUID uuid) {

        File file =
                new File(
                        dataFolder,
                        uuid + ".json"
                );

        if (!file.exists()) {
            return null;
        }

        try (Reader reader =
                     new FileReader(file)) {

            return gson.fromJson(
                    reader,
                    PlayerProfile.class
            );

        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void save(PlayerProfile profile) {

        File file =
                new File(
                        dataFolder,
                        profile.getUuid() + ".json"
                );

        try (Writer writer =
                     new FileWriter(file)) {

            gson.toJson(profile, writer);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public PlayerProfile findByName(
            String playerName
    ) {

        File[] files =
                dataFolder.listFiles();

        if (files == null) {
            return null;
        }

        for (File file : files) {

            if (!file.isFile()
                    || !file.getName().endsWith(".json")) {
                continue;
            }

            try (Reader reader =
                         new FileReader(file)) {

                PlayerProfile profile =
                        gson.fromJson(
                                reader,
                                PlayerProfile.class
                        );

                if (profile != null &&
                        profile.getLastKnownName()
                                .equalsIgnoreCase(
                                        playerName
                                )) {

                    return profile;
                }

            } catch (IOException ignored) {
            }
        }

        return null;
    }
}
