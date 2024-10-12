package nl.onys.resizeplayers.utils;

import nl.onys.resizeplayers.ResizePlayers;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ReachUtils {

    /**
     * Sets the reach of a player based on their scale if the config allows it
     * @param player The player to set the reach of
     * @param scale The scale of the player
     */
    public static void setPlayerReach(Player player, double scale) {
        FileConfiguration config = ResizePlayers.getPlugin().getConfig();
        double blockReach = ReachUtils.calculateReach(scale, false);
        double entityReach = ReachUtils.calculateReach(scale, true);
        if (config.getBoolean("affects-reach")) {
            setPlayerBlockReach(player, blockReach);
            setPlayerEntityReach(player, entityReach);
        } else {
            blockReach = 4.5;
            entityReach = 3.0;
            setPlayerBlockReach(player, blockReach);
            setPlayerEntityReach(player, entityReach);
        }
        PlayerDataUtils.savePlayerReach(player, blockReach, entityReach);
    }

    /**
     * Sets the entity reach of a player
     * @param player The player to set the reach of
     * @param reach The reach to set
     */
    public static void setPlayerEntityReach(Player player, double reach) {
        AttributeInstance reachAttribute = player.getAttribute(Attribute.PLAYER_ENTITY_INTERACTION_RANGE);
        if (reachAttribute == null) return;
        reachAttribute.setBaseValue(reach);
    }

    /**
     * Sets the block reach of a player
     * @param player The player to set the reach of
     * @param reach The reach to set
     */
    public static void setPlayerBlockReach(Player player, double reach) {
        AttributeInstance reachAttribute = player.getAttribute(Attribute.PLAYER_BLOCK_INTERACTION_RANGE);
        if (reachAttribute == null) return;
        reachAttribute.setBaseValue(reach);
    }

    /**
     * Gets the entity reach of a player
     * @param player The player to get the reach of
     * @return The reach of the player
     */
    public static double getPlayerEntityReach(Player player) {
        AttributeInstance reach = player.getAttribute(Attribute.PLAYER_ENTITY_INTERACTION_RANGE);
        if (reach == null) return 3.0;
        return reach.getBaseValue();
    }

    /**
     * Gets the block reach of a player
     * @param player The player to get the reach of
     * @return The reach of the player
     */
    public static double getPlayerBlockReach(Player player) {
        AttributeInstance reach = player.getAttribute(Attribute.PLAYER_BLOCK_INTERACTION_RANGE);
        if (reach == null) return 4.5;
        return reach.getBaseValue();
    }

    /**
     * Calculates the reach of a player or entity based on the scale and the formula in the config
     * @param scale The scale of the player or entity
     * @param isEntity True if the reach is for an entity, false if it's for a player
     * @return The calculated reach
     */
    public static double calculateReach(double scale, boolean isEntity) {
        FileConfiguration config = ResizePlayers.getPlugin().getConfig();
        double defaultBlockReach = 4.5;
        double defaultEntityReach = 3.0;
        double baseReach = isEntity ? defaultEntityReach : defaultBlockReach;

        String formula;
        if (scale >= 1.0) {
            formula = config.getString("reach-formula.scaled-up");
        } else {
            formula = config.getString("reach-formula.scaled-down");
        }
        double exponentialPower = config.getDouble("exponential-power");

        double scaledReach = useFormula(baseReach, scale, formula, exponentialPower);
        if (scaledReach < 0) {
            scaledReach = isEntity ? defaultEntityReach : defaultBlockReach;
        }

        return applyReachLimits(scaledReach, isEntity);
    }

    private static double useFormula(double baseReach, double scale, String formula, double exponentialPower) {
        double scaledReach;
        switch (formula) {
            case "linear":
                scaledReach = baseReach * scale;
                break;
            case "exponential":
                scaledReach = baseReach * Math.pow(scale, exponentialPower);
                break;
            case "logarithmic":
                scaledReach = baseReach * (1 + Math.log(scale));
                break;
            default:
                scaledReach = -1;
                break;
        }
        return scaledReach;
    }

    /**
     * Applies the reach limits to a reach value
     * @param reach The reach value to apply the limits to
     * @param isEntity True if the reach is for an entity, false if it's for a player
     * @return The reach value with the limits applied
     */
    private static double applyReachLimits(double reach, boolean isEntity) {
        FileConfiguration config = ResizePlayers.getPlugin().getConfig();
        double minReach = isEntity ? config.getDouble("entity-reach-limit.min") : config.getDouble("block-reach-limit.min");
        if (reach < minReach) {
            reach = minReach;
        }
        double maxReach = isEntity ? config.getDouble("entity-reach-limit.max") : config.getDouble("block-reach-limit.max");
        if (reach > maxReach) {
            reach = maxReach;
        }
        return reach;
    }

}
