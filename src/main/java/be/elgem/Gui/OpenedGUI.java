package be.elgem.Gui;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class OpenedGUI {
    final private HashMap<Player, GUI> openedGUIs;

    public OpenedGUI() {
        openedGUIs = new HashMap<>();
    }

    public void addGUI(Player player, GUI gui) {
        openedGUIs.remove(player);

        openedGUIs.put(player, gui);
    }

    public GUI getGUI(Player player) {
        return openedGUIs.get(player);
    }
}
