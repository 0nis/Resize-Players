package nl.onys.resizeplayers.utils;

import nl.onys.resizeplayers.ResizePlayers;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ScaleUtils {

    /**
     * Gets the scale of a player
     * @param player The player to get the scale of
     * @return The scale of the player
     */
    public static double getPlayerScale(Player player) {
        AttributeInstance scale = player.getAttribute(Attribute.GENERIC_SCALE);
        if (scale == null) return 1.0;
        return scale.getBaseValue();
    }

    /**
     * Sets the scale of a player, and updates the reach if needed
     * @param player The player to set the scale of
     * @param newScale The new scale to set
     */
    public static void setPlayerScale(Player player, double newScale) {
        AttributeInstance scale = player.getAttribute(Attribute.GENERIC_SCALE);
        if (scale == null) return;
        scale.setBaseValue(newScale);
        PlayerDataUtils.savePlayerHeight(player, convertScaleToBlocks(newScale));
        ReachUtils.setPlayerReach(player, newScale);
    }

    /**
     * Converts a scale to blocks
     * @param scale The scale to convert
     * @return The blocks equivalent of the scale
     */
    public static double convertScaleToBlocks(double scale) {
        return scale * 1.8;
    }

    /**
     * Converts blocks to a scale
     * @param blocks The blocks to convert
     * @return The scale equivalent of the blocks
     */
    public static double convertBlocksToScale(double blocks) {
        return blocks / 1.8;
    }

    /**
     * Checks if the given size is a valid number and within the limits
     * @param commandExec The command executor
     * @param size The size to check
     * @return True if the size is valid, false otherwise
     */
    public static boolean isValidSize(Player commandExec, String size) {
        if (size.equalsIgnoreCase("default")) return true;

        FileConfiguration config = ResizePlayers.getPlugin().getConfig();
        double minBlocks = config.getDouble("block-height-limit.min");
        double maxBlocks = config.getDouble("block-height-limit.max");

        double blockSize;
        try {
            blockSize = Double.parseDouble(size);
        } catch (NumberFormatException e) {
            MessageUtils.onInvalidScale(commandExec, minBlocks, maxBlocks);
            return false;
        }

        if ((blockSize < minBlocks || blockSize > maxBlocks)) {
            if (!commandExec.hasPermission("resizeplayers.scale.bypass")) {
                MessageUtils.onInvalidScale(commandExec, minBlocks, maxBlocks);
                return false;
            } else {
                MessageUtils.onBypassed(commandExec, minBlocks, maxBlocks);
            }
        }

        return true;
    }

}
