package org.leeanh.TeffaCoreAPI.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.leeanh.TeffaCoreAPI.core.TeffaCorePlugin;
import org.leeanh.TeffaCoreAPI.client.TeffaClientSession;
import org.leeanh.TeffaCoreAPI.client.TeffaClientStatus;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TeffaCommand implements CommandExecutor {

    private final TeffaCorePlugin plugin;

    private final DateTimeFormatter formatter =
            DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

    public TeffaCommand(
            TeffaCorePlugin plugin
    ) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (args.length == 0) {

            sender.sendMessage("§cDùng: /teffa clientstatus <player>");
            return true;
        }

        if (args[0].equalsIgnoreCase("clientstatus")) {

            handleClientStatus(
                    sender,
                    args
            );

            return true;
        }

        sender.sendMessage("§cLệnh không tồn tại.");
        sender.sendMessage("§7Dùng: /teffa clientstatus <player>");

        return true;
    }

    private void handleClientStatus(
            CommandSender sender,
            String[] args
    ) {

        if (!sender.hasPermission("teffa.command.clientstatus")) {

            sender.sendMessage("§cBạn không có quyền dùng lệnh này.");
            return;
        }

        if (args.length < 2) {

            sender.sendMessage("§cDùng: /teffa clientstatus <player>");
            return;
        }

        Player target =
                Bukkit.getPlayerExact(
                        args[1]
                );

        if (target == null) {

            sender.sendMessage("§cPlayer không online.");
            return;
        }

        Optional<TeffaClientSession> optionalSession =
                plugin.getTeffaClientSessionManager()
                        .getSession(
                                target
                        );

        if (optionalSession.isEmpty()) {

            sender.sendMessage("§8§m------------------------------");
            sender.sendMessage("§aTeffaClient Status");
            sender.sendMessage("§7Player: §f" + target.getName());
            sender.sendMessage("§7Status: §c" + TeffaClientStatus.NONE);
            sender.sendMessage("§7Session: §cNone");
            sender.sendMessage("§8§m------------------------------");
            return;
        }

        TeffaClientSession session =
                optionalSession.get();

        sender.sendMessage("§8§m------------------------------");
        sender.sendMessage("§aTeffaClient Status");
        sender.sendMessage("§7Player: §f" + target.getName());
        sender.sendMessage("§7Status: §f" + session.getStatus());
        sender.sendMessage("§7Verified: §f" + session.isVerified());
        sender.sendMessage("§7Expired: §f" + session.isExpired());
        sender.sendMessage("§7SessionId: §f" + session.getSessionId());
        sender.sendMessage("§7ClientVersion: §f" + safe(session.getClientVersion()));
        sender.sendMessage("§7Protocol: §f" + session.getProtocolVersion());
        sender.sendMessage("§7LastSequence: §f" + session.getLastSequence());
        sender.sendMessage("§7ExpiresAt: §f" + formatter.format(session.getExpiresAt()));
        sender.sendMessage("§8§m------------------------------");
    }

    private String safe(
            String value
    ) {

        if (value == null || value.isBlank()) {
            return "none";
        }

        return value;
    }
}