package be.elgem.Listeners;

import be.elgem.Jobs.Misc.EWayToXP;
import be.elgem.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        if(event.getPlayer() != null) {
            Main.getMain().getServerWideJobHandler().getPlayerJobsHandle(event.getPlayer().getUniqueId()).addXpToJob(EWayToXP.BREAK, "minecraft:oak_log");
        }
    }
}
