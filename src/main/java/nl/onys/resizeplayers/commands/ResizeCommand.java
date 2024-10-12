package nl.onys.resizeplayers.commands;

import nl.onys.resizeplayers.utils.MessageUtils;
import nl.onys.resizeplayers.utils.ScaleUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ResizeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            MessageUtils.onOnlyPlayers(commandSender);
            return true;
        }

        Player player = (Player) commandSender;

        switch (args.length) {
            case 1:
                return resizeSelf(player, args[0]);
            case 2:
                if (args[1].equalsIgnoreCase("all")) {
                    return resizeAll(player, args[0]);
                }
                return resizeOther(player, args[0], args[1]);
            default:
                MessageUtils.onWrongUsage(player, "/resize <blocks> [player]");
                return false;
        }
    }

    /**
     * Resize the command sender
     * @param player The player to resize
     * @param size The size to resize to in blocks
     * @return True if the player was resized, false otherwise
     */
    private boolean resizeSelf(Player player, String size) {
        if (!player.hasPermission("resizeplayers.scale.self") && !player.isOp()) {
            MessageUtils.onNoPermission(player, "resizeplayers.scale.self");
            return false;
        }
        double[] sizes = validateAndConvertSize(player, size);
        if (sizes == null) {
            return false;
        }
        double blocksSize = sizes[0];
        double scaleSize = sizes[1];

        ScaleUtils.setPlayerScale(player, scaleSize, true, true);
        MessageUtils.onScaledSelf(player, blocksSize);
        return true;
    }

    /**
     * Resize another player
     * @param player The player resizing the target
     * @param size The size to resize to in blocks
     * @param target The target player to resize
     * @return True if the target was resized, false otherwise
     */
    private boolean resizeOther(Player player, String size, String target) {
        if (!player.hasPermission("resizeplayers.scale.others") && !player.isOp()) {
            MessageUtils.onNoPermission(player, "resizeplayers.scale.others");
            return false;
        }
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer == null) {
            MessageUtils.onPlayerNotFound(player, target);
            return false;
        }
        if (targetPlayer.hasPermission("resizeplayers.scale.exempt") && player != targetPlayer) {
            MessageUtils.onTargetExempt(player, targetPlayer.getName());
            return false;
        }
        double[] sizes = validateAndConvertSize(player, size);
        if (sizes == null) {
            return false;
        }
        double blocksSize = sizes[0];
        double scaleSize = sizes[1];

        ScaleUtils.setPlayerScale(targetPlayer, scaleSize, true, true);
        MessageUtils.onScaledOther(player, blocksSize, targetPlayer.getName());
        return true;
    }

    /**
     * Resize all players
     * @param player The player resizing all players
     * @param size The size to resize to in blocks
     * @return True if all players were resized, false otherwise
     */
    private boolean resizeAll(Player player, String size) {
        if (!player.hasPermission("resizeplayers.scale.all") && !player.isOp()) {
            MessageUtils.onNoPermission(player, "resizeplayers.scale.all");
            return false;
        }
        double[] sizes = validateAndConvertSize(player, size);
        if (sizes == null) {
            return false;
        }
        double blocksSize = sizes[0];
        double scaleSize = sizes[1];

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("resizeplayers.scale.exempt")) {
                MessageUtils.onTargetExempt(player, onlinePlayer.getName());
                continue;
            }
            ScaleUtils.setPlayerScale(onlinePlayer, scaleSize, true, true);
        }
        MessageUtils.onScaledAll(player, blocksSize);
        return true;
    }

    /**
     * Validate and convert the size to blocks and scale
     * @param player The player resizing
     * @param size The size to validate and convert
     * @return The size in blocks and scale if valid, null otherwise
     */
    private double[] validateAndConvertSize(Player player, String size) {
        if (!ScaleUtils.isValidSize(player, size)) {
            return null;
        }
        double blocksSize;
        double scaleSize;
        if (size.equalsIgnoreCase("default")) {
            scaleSize = 1.0;
            blocksSize = ScaleUtils.convertScaleToBlocks(scaleSize);
        } else {
            blocksSize = Double.parseDouble(size);
            scaleSize = ScaleUtils.convertBlocksToScale(blocksSize);
        }
        return new double[]{blocksSize, scaleSize};
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        switch (args.length) {
            case 1: {
                String[] defaultOptions = {"0.5", "0.75", "1", "1.25", "1.5", "1.75", "2", "2.25", "2.5", "2.75", "3", "default"};
                String filter = args[0].toLowerCase();
                List<String> optionsList = new ArrayList<>();
                for (String option : defaultOptions) {
                    if (option.toLowerCase().startsWith(filter)) {
                        optionsList.add(option);
                    }
                }
                return optionsList;
            }
            case 2: {
                String filter = args[1].toLowerCase();
                List<String> playerNames = new ArrayList<>();
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.getName().toLowerCase().startsWith(filter)) {
                        playerNames.add(onlinePlayer.getName());
                    }
                }
                playerNames.add("all");
                return playerNames;
            }
            default:
                return null;
        }
    }
}
