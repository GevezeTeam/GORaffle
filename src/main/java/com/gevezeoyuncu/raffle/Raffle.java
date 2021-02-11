package com.gevezeoyuncu.raffle;

import com.gevezeoyuncu.raffle.utils.Randomize;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class Raffle {

    private String key;
    private String message;
    private List<String> excludedPermissions;
    private List<String> commands;

    Raffle(String key, ConfigurationSection section) {
        this.key = key;
        message = section.getString("Message");
        excludedPermissions = section.getStringList("ExcludedPermissions");
        commands = section.getStringList("Commands");
    }

    @SuppressWarnings({"unchecked", "Convert2MethodRef"})
    public void start() throws Exception {
        List<Player> allPlayers = Bukkit.getOnlinePlayers().stream()
                .filter(player -> !player.isOp())
                .filter(player -> getExcludedPermissions().stream().noneMatch(permission -> player.hasPermission(permission)))
                .collect(Collectors.toList());
        Randomize<Player> randomize = Randomize.create(allPlayers).shuffle();
        Player selectedPlayer = randomize.get();
        if (selectedPlayer == null) throw new Exception("Herhangi bir oyuncu seçilemedi!");
        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            loopPlayer.sendMessage(getMessage().replace("%player%", selectedPlayer.getName()));
        }
        for (String command : getCommands()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", selectedPlayer.getName()));
        }
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getExcludedPermissions() {
        return excludedPermissions;
    }

    public List<String> getCommands() {
        return commands;
    }
}
