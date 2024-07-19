package nl.onys.resizeplayers.integrations.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import nl.onys.resizeplayers.ResizePlayers;
import nl.onys.resizeplayers.utils.PlayerDataUtils;
import nl.onys.resizeplayers.utils.ScaleUtils;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class ResizePlayersPlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return ResizePlayers.getPlugin().getName().toLowerCase();
    }

    @Override
    public @NotNull String getAuthor() {
        return ResizePlayers.getPlugin().getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return ResizePlayers.getPlugin().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("height")) {
            return String.valueOf(PlayerDataUtils.getPlayerHeight(player));
        } else if (params.equalsIgnoreCase("block-reach")) {
            return String.valueOf(PlayerDataUtils.getPlayerBlockReach(player));
        } else if (params.equalsIgnoreCase("entity-reach")) {
            return String.valueOf(PlayerDataUtils.getPlayerEntityReach(player));
        }
        return null;
    }
}
