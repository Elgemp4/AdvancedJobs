package be.elgem.Gui;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class OpenedGUI {
    private HashMap<Player, GUI> openedGUIs;

    public OpenedGUI() {
        openedGUIs = new HashMap<>();
    }

    public void addGUI(Player player, GUI gui) {
        if(openedGUIs.containsKey(player)) {
            openedGUIs.remove(player);
        }

        openedGUIs.put(player, gui);
    }

//    public void removeGUI(Player player) {
//        if(openedGUIs.containsKey(player)) {
//            GUI gui = openedGUIs.get(player);
//
//            if(gui instanceof JobSettingsModifierGUI) {
//                if(((JobSettingsModifierGUI)gui).isChoosingAnItem()) {
//                    return;
//                }
//            }
//
//            openedGUIs.remove(player);
//        }
//    }


    public GUI getGUI(Player player) {
        return openedGUIs.get(player);
    }
}
