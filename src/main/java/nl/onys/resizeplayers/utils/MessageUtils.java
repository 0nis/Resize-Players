package nl.onys.resizeplayers.utils;

import nl.onys.resizeplayers.ResizePlayers;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessageUtils {

    /**
     * Formats a message with color codes
     * @param commandSender The command sender to replace the placeholders for (null if console)
     * @param message The message to format
     * @return The formatted message
     */
    public static String formatMessage(CommandSender commandSender, String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Sends the help message to the command sender
     * @param commandSender The command sender to send the help message to
     */
    public static void onHelpMessage(CommandSender commandSender) {
        FileConfiguration config = ResizePlayers.getPlugin().getConfig();
        List<String> helpCommandLines = config.getStringList("help-command-lines");
        for (String line : helpCommandLines) {
            line  = line
                    .replace("{name}", ResizePlayers.getPlugin().getDescription().getName())
                    .replace("{version}", ResizePlayers.getPlugin().getDescription().getVersion())
                    .replace("{author}", ResizePlayers.getPlugin().getDescription().getAuthors().get(0));
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    /**
     * Sends player height and reach information to the command sender
     * @param commandSender The command sender to send the information to
     * @param target The player to get the information from
     * @param blockHeight The height of the player in blocks
     * @param blockReach The block reach of the player
     * @param entityReach The entity reach of the player
     */
    public static void onInfoMessage(CommandSender commandSender, String target, String blockHeight, String blockReach, String entityReach) {
        FileConfiguration config = ResizePlayers.getPlugin().getConfig();
        List<String> infoCommandLines = config.getStringList("info-message");
        for (String line : infoCommandLines) {
            line  = line
                    .replace("{player}", target)
                    .replace("{height}", blockHeight)
                    .replace("{block-reach}", blockReach)
                    .replace("{entity-reach}", entityReach);
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    public static void onNoPermission(CommandSender sender, String permission) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("no-permission")
                        .replace("{permission}", permission)
        ));
    }

    public static void onWrongUsage(CommandSender sender, String usage) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("wrong-usage")
                        .replace("{usage}", usage)
        ));
    }

    public static void onOnlyPlayers(CommandSender sender) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("only-players")
        ));
    }

    public static void onPlayerNotFound(CommandSender sender, String target) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("player-not-found")
                        .replace("{target}", target)
        ));
    }

    public static void onConfigReloaded(CommandSender sender) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("config-reloaded")
        ));
    }

    public static void onUnknownError(CommandSender sender) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("unknown-error")
        ));
    }

    public static void onScaledSelf(CommandSender sender, double scale) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("scaled-self")
                        .replace("{blocks}", String.valueOf(scale))
        ));
    }

    public static void onScaledOther(CommandSender sender, double scale, String target) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("scaled-other")
                        .replace("{blocks}", String.valueOf(scale))
                        .replace("{target}", target)
        ));
    }

    public static void onScaledAll(CommandSender sender, double scale) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("scaled-all")
                        .replace("{blocks}", String.valueOf(scale))
        ));
    }

    public static void onBypassed(CommandSender sender, double min, double max) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("on-bypassed")
                        .replace("player", sender.getName())
                        .replace("{min}", String.valueOf(min))
                        .replace("{max}", String.valueOf(max))
        ));
    }

    public static void onInvalidScale(CommandSender sender, double min, double max) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("invalid-scale")
                        .replace("{min}", String.valueOf(min))
                        .replace("{max}", String.valueOf(max))
        ));
    }

    public static void onTargetExempt(CommandSender sender, String target) {
        sender.sendMessage(formatMessage(sender,
                ResizePlayers.getPlugin().getConfig().getString("target-exempt")
                        .replace("{target}", target)
        ));
    }

}
