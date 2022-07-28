package be.elgem.Listeners;

import be.elgem.Gui.Admin.JobSettingsModifierGUI;
import be.elgem.Gui.GUI;
import be.elgem.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        GUI gui = Main.getMain().getOpenedGUI().getGUI(event.getPlayer());
        if(gui.isWaitingForInput()) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> gui.getInput(event.getMessage()), 1);
        }
    }
}
