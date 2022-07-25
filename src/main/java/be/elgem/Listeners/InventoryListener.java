package be.elgem.Listeners;

import be.elgem.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(Main.getMain().getOpenedGUI().getGUI(player) == null) {return;}

        if(event.getInventory().equals(Main.getMain().getOpenedGUI().getGUI(player).getMenu())) {
            event.setCancelled(true);
            Main.getMain().getOpenedGUI().getGUI((Player) event.getWhoClicked()).executeActionForClick(event.getSlot());
        }
    }

//    @EventHandler
//    public void onInventoryExit(InventoryCloseEvent event) {
//        Player player = (Player) event.getPlayer();
//
//        Main.getMain().getOpenedGUI().removeGUI(player);
//    }
}
