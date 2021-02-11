package com.gevezeoyuncu.raffle;

import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class RaffleManager {

    private List<Raffle> raffles = new ArrayList<>();

    public RaffleManager() throws Exception {
        ConfigurationSection section = RafflePlugin.getInstance().getConfig().getConfigurationSection("Raffles");
        if (section == null) throw new Exception("Ayar dosyası hatalı!");
        for (String raffleKey : section.getKeys(false)) {
            ConfigurationSection raffleSection = section.getConfigurationSection(raffleKey);
            if (raffleSection == null) continue;
            if (!raffleSection.contains("Commands") || !raffleSection.isList("Commands")) continue;
            if (!raffleSection.contains("Message") || !raffleSection.isString("Message")) continue;
            if (!raffleSection.contains("ExcludedPermissions") || !raffleSection.isList("ExcludedPermissions")) continue;
            Raffle raffle = new Raffle(raffleKey, raffleSection);
            raffles.add(raffle);
        }
    }

    @Nullable
    public Raffle get(String raffleKey) {
        return raffles.stream().filter(raffle -> raffle.getKey().equals(raffleKey)).findFirst().orElse(null);
    }

    public List<Raffle> getRaffles() {
        return raffles;
    }
}
