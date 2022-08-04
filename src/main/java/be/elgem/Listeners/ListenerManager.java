package be.elgem.Listeners;

import be.elgem.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class ListenerManager {
    Main main;

    ArrayList<Listener> listeners;

    public ListenerManager() {
        main = Main.getMain();

        listeners = new ArrayList<>();

        addListeners();

        registerListeners();
    }


    private void addListeners() {
        listeners.add(new ChunkLoadingListener());

        listeners.add(new PlayerJoinAndQuit());

        listeners.add(new BlockBreakListener());

        listeners.add(new InventoryListener());

        listeners.add(new ItemUseListener());

        listeners.add(new ChatListener());

        listeners.add(new MobKillListener());
    }

    private void registerListeners() {
        listeners.forEach((listener) ->{
            main.getServer().getPluginManager().registerEvents(listener, main);
        });
    }

    public static boolean canGainExperience(Player player) {
        return player != null && player.getGameMode() != GameMode.CREATIVE;
    }


}
