package org.leeanh.TeffaCoreAPI.client;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class TeffaClientBridge implements PluginMessageListener {

    public static final String CHANNEL_ACTIVE =
            "teffa:active";

    public static final String CHANNEL_CLIENT_HELLO =
            "teffa:client_hello";

    private static final int SERVER_PROTOCOL_VERSION =
            1;

    private final Plugin plugin;
    private final TeffaClientSessionManager sessionManager;

    public TeffaClientBridge(
            Plugin plugin,
            TeffaClientSessionManager sessionManager
    ) {
        this.plugin = plugin;
        this.sessionManager = sessionManager;
    }

    public void registerChannels() {

        Bukkit.getMessenger().registerOutgoingPluginChannel(
                plugin,
                CHANNEL_ACTIVE
        );

        Bukkit.getMessenger().registerIncomingPluginChannel(
                plugin,
                CHANNEL_CLIENT_HELLO,
                this
        );
    }

    public void unregisterChannels() {

        Bukkit.getMessenger().unregisterOutgoingPluginChannel(
                plugin,
                CHANNEL_ACTIVE
        );

        Bukkit.getMessenger().unregisterIncomingPluginChannel(
                plugin,
                CHANNEL_CLIENT_HELLO,
                this
        );
    }

    public void sendActive(Player player) {

        TeffaClientSession session =
                sessionManager.createPendingSession(player);

        String payload =
                "ACTIVE"
                        + ";"
                        + session.getSessionId()
                        + ";"
                        + session.getServerNonce()
                        + ";"
                        + SERVER_PROTOCOL_VERSION;

        byte[] data =
                payload.getBytes(StandardCharsets.UTF_8);

        player.sendPluginMessage(
                plugin,
                CHANNEL_ACTIVE,
                data
        );

        plugin.getLogger().info(
                "Sent teffa:active to "
                        + player.getName()
                        + " session="
                        + session.getSessionId()
        );

        Bukkit.getScheduler().runTaskLater(
                plugin,
                () -> {
                    Optional<TeffaClientSession> optionalSession =
                            sessionManager.getSession(player);

                    if (optionalSession.isEmpty()) {
                        return;
                    }

                    TeffaClientSession currentSession =
                            optionalSession.get();

                    if (currentSession.getStatus()
                            == TeffaClientStatus.PENDING_HANDSHAKE) {

                        currentSession.setStatus(
                                TeffaClientStatus.NO_CLIENT
                        );

                        plugin.getLogger().info(
                                player.getName()
                                        + " did not respond to TeffaClient handshake."
                        );
                    }
                },
                100L
        );
    }



    @Override
    public void onPluginMessageReceived(
            String channel,
            Player player,
            byte[] message
    ) {

        if (!channel.equals(CHANNEL_CLIENT_HELLO)) {
            return;
        }

        String payload =
                new String(
                        message,
                        StandardCharsets.UTF_8
                );

        handleClientHello(
                player,
                payload
        );
    }

    private void handleClientHello(
            Player player,
            String payload
    ) {

        Optional<TeffaClientSession> optionalSession =
                sessionManager.getSession(player);

        if (optionalSession.isEmpty()) {
            return;
        }

        TeffaClientSession session =
                optionalSession.get();

        if (session.isExpired()) {

            session.setStatus(
                    TeffaClientStatus.FAILED
            );

            return;
        }

        String[] parts =
                payload.split(";");

        if (parts.length < 4) {

            session.setStatus(
                    TeffaClientStatus.FAILED
            );

            return;
        }

        String type =
                parts[0];

        String sessionId =
                parts[1];

        String serverNonce =
                parts[2];

        String clientVersion =
                parts[3];

        int protocolVersion =
                parseProtocolVersion(parts);

        if (!type.equals("CLIENT_HELLO")) {

            session.setStatus(
                    TeffaClientStatus.FAILED
            );

            return;
        }

        if (!session.getSessionId().equals(sessionId)) {

            session.setStatus(
                    TeffaClientStatus.FAILED
            );

            return;
        }

        if (!session.getServerNonce().equals(serverNonce)) {

            session.setStatus(
                    TeffaClientStatus.FAILED
            );

            return;
        }

        if (protocolVersion != SERVER_PROTOCOL_VERSION) {

            session.setStatus(
                    TeffaClientStatus.UNSUPPORTED_VERSION
            );

            return;
        }

        session.setClientVersion(
                clientVersion
        );

        session.setProtocolVersion(
                protocolVersion
        );

        session.setStatus(
                TeffaClientStatus.VERIFIED
        );

        plugin.getLogger().info(
                player.getName()
                        + " verified TeffaClient "
                        + clientVersion
        );
    }

    private int parseProtocolVersion(String[] parts) {

        if (parts.length < 5) {
            return -1;
        }

        try {
            return Integer.parseInt(parts[4]);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}