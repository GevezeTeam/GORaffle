package com.gevezeoyuncu.raffle;

import com.gevezeoyuncu.raffle.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class RaffleCommand implements CommandExecutor {

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Text.colorize("&cKomutu eksik kullandın!"));
            return true;
        }
        if (!sender.hasPermission("go.raffle")) {
            sender.sendMessage(Text.colorize("&cBu komutu kullanmak için gereken yetkiye sahip değilsin!"));
            return true;
        }
        if (args[0].equals("reload")) {
            try {
                RafflePlugin.getInstance().reload();
                sender.sendMessage(Text.colorize("&aEklenti yenilendi!"));
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(Text.colorize("&cEklenti yenilenirken hata oluştu konsolu kontrol etmeyi unutma!"));
            }
        } else if (args[0].equals("start") && args.length >= 2) {
            String key = args[1];
            Raffle raffle = RafflePlugin.getInstance().getManager().get(key);
            if (raffle == null) {
                sender.sendMessage(Text.colorize("&a" + key + " &7adında bir çekiliş yok!"));
                return true;
            }
            try {
                for (Player loopPlayer : Bukkit.getOnlinePlayers().stream()
                        .filter(player -> player.hasPermission("go.raffle"))
                        .collect(Collectors.toList())) {
                    loopPlayer.sendMessage(Text.colorize("&a" + key + " &7adındaki çekiliş &e" + sender.getName() + " &7adlı yetkili tarafından başlatıldı!"));
                }
                raffle.start();
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(Text.colorize("&cÇekiliş başlatılırken hata oluştu konsolu kontrol etmeyi unutma!"));
            }
        }
        return true;
    }
}
