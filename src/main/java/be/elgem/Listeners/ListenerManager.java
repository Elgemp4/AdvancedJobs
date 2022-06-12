package be.elgem.Listeners;

import be.elgem.Main;
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
        listeners.add(new PlayerJoinAndQuit());

        listeners.add(new BlockBreakListener());
    }

    private void registerListeners() {
        listeners.forEach((listener) ->{
            main.getServer().getPluginManager().registerEvents(listener, main);
        });
    }
}
