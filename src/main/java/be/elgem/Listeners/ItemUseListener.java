package be.elgem.Listeners;

import be.elgem.Gui.GUI;
import be.elgem.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemUseListener implements Listener {
    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {return;}
        if(event.getItem() == null) {return;}

        GUI gui = Main.getMain().getOpenedGUI().getGUI(event.getPlayer());

        if(gui.isWaitingForItemSelection()) {
            event.setCancelled(true);

            gui.getItemSelection(event.getItem());
            gui.openInventory();
        }


    }
}
