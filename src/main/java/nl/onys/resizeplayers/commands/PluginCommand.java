package nl.onys.resizeplayers.commands;

import nl.onys.resizeplayers.ResizePlayers;
import nl.onys.resizeplayers.utils.MessageUtils;
import nl.onys.resizeplayers.utils.ReachUtils;
import nl.onys.resizeplayers.utils.ScaleUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PluginCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        // Sorry I know this function is very messy
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            MessageUtils.onHelpMessage(commandSender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (commandSender.hasPermission("resizeplayers.reload") || commandSender.isOp()) {
                ResizePlayers.getPlugin().reloadConfig();
                MessageUtils.onConfigReloaded(commandSender);
            } else {
                MessageUtils.onNoPermission(commandSender, "resizeplayers.reload");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            if (args.length == 1) {
                if (!commandSender.hasPermission("resizeplayers.info.self") && !commandSender.isOp()) {
                    MessageUtils.onNoPermission(commandSender, "resizeplayers.info.self");
                    return false;
                }
                if (!(commandSender instanceof Player)) {
                    MessageUtils.onOnlyPlayers(commandSender);
                    return true;
                }
                Player player = (Player) commandSender;
                return sendInfoMessage(commandSender, player);
            } else if (args.length == 2) {
                if (!commandSender.hasPermission("resizeplayers.info.others") && !commandSender.isOp()) {
                    MessageUtils.onNoPermission(commandSender, "resizeplayers.info.others");
                    return false;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) {
                    MessageUtils.onPlayerNotFound(commandSender, args[1]);
                    return true;
                }
                return sendInfoMessage(commandSender, player);
            } else {
                MessageUtils.onWrongUsage(commandSender, "/resizeplayers info [player]");
            }
        }
        return false;
    }

    /**
     * Sends player height and reach information to the command sender
     * @param commandSender The command sender to send the information to
     * @param player The player to get the information from
     */
    private boolean sendInfoMessage(CommandSender commandSender, Player player) {
        DecimalFormat df = new DecimalFormat("#.##");
        String blockHeight = df.format(ScaleUtils.convertScaleToBlocks(ScaleUtils.getPlayerScale(player)));
        String blockReach = df.format(ReachUtils.getPlayerBlockReach(player));
        String entityReach = df.format(ReachUtils.getPlayerEntityReach(player));

        MessageUtils.onInfoMessage(commandSender, player.getName(), blockHeight, blockReach, entityReach);
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            if (commandSender.hasPermission("resizeplayers.reload")) {
                completions.add("reload");
            }
            if (commandSender.hasPermission("resizeplayers.info")) {
                completions.add("info");
            }
            completions.add("help");
            return completions;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("info")) {
            List<String> completions = new ArrayList<>();
            if (commandSender.hasPermission("resizeplayers.info.others")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            }
            return completions;
        }
        return Collections.emptyList();
    }
}
