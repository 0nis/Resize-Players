package nl.onys.resizeplayers;

import nl.onys.resizeplayers.commands.PluginCommand;
import nl.onys.resizeplayers.commands.ResizeCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ResizePlayers extends JavaPlugin {

    private static ResizePlayers plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getLogger().info("ResizePlayers has been enabled. Hello :D");
        saveDefaultConfig();
        setCommandExecutor("resize", new ResizeCommand());
        setCommandExecutor("resizeplayers", new PluginCommand());
    }

    private void setCommandExecutor(String command, CommandExecutor executor) {
        org.bukkit.command.PluginCommand pluginCommand = plugin.getCommand(command);
        if (pluginCommand != null) pluginCommand.setExecutor(executor);
        else Bukkit.getLogger().warning("Could not set command executor for " + command + "!");
    }

    private void registerEvent(Listener listener) {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("ResizePlayers has been disabled. Bye :(");
    }

    public static ResizePlayers getPlugin() {
        return plugin;
    }
}
