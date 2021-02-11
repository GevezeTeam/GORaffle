package com.gevezeoyuncu.raffle;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("WeakerAccess")
public class RafflePlugin extends JavaPlugin {

    private static RafflePlugin instance;

    private RaffleManager manager;

    @Override
    public void onDisable() {

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        try {
            manager = new RaffleManager();
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        getCommand("raffle").setExecutor(new RaffleCommand());
    }

    public void reload() throws Exception {
        saveDefaultConfig();
        manager = new RaffleManager();
    }

    public static RafflePlugin getInstance() {
        return instance;
    }

    public RaffleManager getManager() {
        return manager;
    }
}
