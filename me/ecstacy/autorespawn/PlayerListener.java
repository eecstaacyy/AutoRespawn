package me.ecstacy.autorespawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListener implements Listener {
    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        String title;
        String subtitle = "";

        if (killer != null) {
            title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.killedByPlayer").replace("{killer}", killer.getName()));
        } else {
            title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.died"));
        }

        int respawnTime = plugin.getConfig().getInt("respawn.time");
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.spigot().respawn();
            playRespawnEffects(player);
            player.sendTitle(title, subtitle, 10, 70, 20);
        }, respawnTime * 20L);
    }

    private void playRespawnEffects(Player player) {
        FileConfiguration config = plugin.getConfig();

        String soundName = config.getString("respawn.sound", "ENTITY_BAT_DEATH");
        Sound sound = Sound.valueOf(soundName);
        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);

        String effectName = config.getString("respawn.effect", "VILLAGER_HAPPY");
        Particle effect = Particle.valueOf(effectName);
        player.getWorld().spawnParticle(effect, player.getLocation(), 30, 0.5, 1, 0.5, 0.01);
    }
}
