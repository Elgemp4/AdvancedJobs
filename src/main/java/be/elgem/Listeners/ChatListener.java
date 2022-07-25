package be.elgem.Listeners;

import be.elgem.Gui.JobSettingsModifierGUI;
import be.elgem.Gui.OpenedGUI;
import be.elgem.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(Main.getMain().getOpenedGUI().getGUI(event.getPlayer()) instanceof JobSettingsModifierGUI) {
            JobSettingsModifierGUI gui = (JobSettingsModifierGUI) Main.getMain().getOpenedGUI().getGUI(event.getPlayer());
            if(gui.isInputing()) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> {
                    gui.sendInput(event.getMessage());
                }, 1);
            }
        }
    }
}
