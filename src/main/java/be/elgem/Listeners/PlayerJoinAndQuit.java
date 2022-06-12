package be.elgem.Listeners;

import be.elgem.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinAndQuit implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Main.getMain().getServerWideJobHandler().addJobHandler(event.getPlayer().getUniqueId());
        System.out.println("ici");
    }
}
