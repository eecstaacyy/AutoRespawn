package me.ecstacy.autorespawn;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getLogger().info("The plugin has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled!");
    }
}
