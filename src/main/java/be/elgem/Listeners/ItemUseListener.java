package be.elgem.Listeners;

import be.elgem.Gui.JobSettingsModifierGUI;
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

        if(Main.getMain().getOpenedGUI().getGUI(event.getPlayer()) instanceof JobSettingsModifierGUI) {
            JobSettingsModifierGUI gui = (JobSettingsModifierGUI) Main.getMain().getOpenedGUI().getGUI(event.getPlayer());
            if(gui.isChoosingAnItem()) {
                event.setCancelled(true);

                gui.setNewIcon(event.getItem().getType());
                gui.openInventory();
            }
        }


    }
}
