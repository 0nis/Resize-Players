package nl.onys.resizeplayers.utils;

import nl.onys.resizeplayers.configs.PlayerData;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerDataUtils {

    /**
     * Saves the height of a player to the player data file in blocks.
     * @param player The player to save the height of
     * @param height The height of the player in blocks
     */
    public synchronized static void savePlayerHeight(Player player, double height) {
        ConfigurationSection playerDataSection = PlayerData.get().getConfigurationSection(player.getUniqueId().toString());
        if (playerDataSection == null) {
            playerDataSection = PlayerData.get().createSection(player.getUniqueId().toString());
        }
        playerDataSection.set("username", player.getName());
        playerDataSection.set("height", height);
        PlayerData.save();
    }

    /**
     * Saves the reach of a player to the player data file.
     * @param player The player to save the reach of
     * @param blockReach The block reach of the player
     * @param entityReach The entity reach of the player
     */
    public synchronized static void savePlayerReach(Player player, double blockReach, double entityReach) {
        ConfigurationSection playerDataSection = PlayerData.get().getConfigurationSection(player.getUniqueId().toString());
        if (playerDataSection == null) {
            playerDataSection = PlayerData.get().createSection(player.getUniqueId().toString());
        }
        playerDataSection.set("username", player.getName());
        playerDataSection.set("block-reach", blockReach);
        playerDataSection.set("entity-reach", entityReach);
        PlayerData.save();
    }

    /**
     * Gets the UUID of a player from their username
     * @param playerName The username of the player
     * @return The UUID of the player. Null if the player is not found
     */
    public static String getUUIDFromUsername(String playerName) {
        for (String key : PlayerData.get().getKeys(false)) {
            if (PlayerData.get().getString(key + ".username").equalsIgnoreCase(playerName)) {
                return key;
            }
        }
        return null;
    }

    /**
     * Gets the height of a player in blocks
     * @param player The player to get the height of
     * @return The height of the player in blocks
     */
    public static double getPlayerHeight(Player player) {
        return getPlayerHeight((OfflinePlayer) player);
    }

    /**
     * Gets the block reach of a player
     * @param player The player to get the block reach of
     * @return The block reach of the player
     */
    public static double getPlayerBlockReach(Player player) {
        return getPlayerBlockReach((OfflinePlayer) player);
    }

    /**
     * Gets the entity reach of a player
     * @param player The player to get the entity reach of
     * @return The entity reach of the player
     */
    public static double getPlayerEntityReach(Player player) {
        return getPlayerEntityReach((OfflinePlayer) player);
    }

    /**
     * Gets the height of an offline player in blocks
     * @param player The player to get the height of
     * @return The height of the player in blocks
     */
    public static double getPlayerHeight(OfflinePlayer player) {
        ConfigurationSection playerDataSection = PlayerData.get().getConfigurationSection(player.getUniqueId().toString());
        if (playerDataSection == null) return 1.8;
        return playerDataSection.getDouble("height", 1.8);
    }

    /**
     * Gets the block reach of an offline player
     * @param player The player to get the block reach of
     * @return The block reach of the player
     */
    public static double getPlayerBlockReach(OfflinePlayer player) {
        ConfigurationSection playerDataSection = PlayerData.get().getConfigurationSection(player.getUniqueId().toString());
        if (playerDataSection == null) return 4.5;
        return playerDataSection.getDouble("block-reach", 4.5);
    }

    /**
     * Gets the entity reach of an offline player
     * @param player The player to get the entity reach of
     * @return The entity reach of the player
     */
    public static double getPlayerEntityReach(OfflinePlayer player) {
        ConfigurationSection playerDataSection = PlayerData.get().getConfigurationSection(player.getUniqueId().toString());
        if (playerDataSection == null) return 3.0;
        return playerDataSection.getDouble("entity-reach", 3.0);
    }
}
