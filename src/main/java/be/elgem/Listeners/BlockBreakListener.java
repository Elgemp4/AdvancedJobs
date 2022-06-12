package be.elgem.Listeners;

import be.elgem.Jobs.Misc.EWayToXP;
import be.elgem.Jobs.Player.ServerWideJobHandler;
import be.elgem.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private ServerWideJobHandler serverWideJobHandler;

    public BlockBreakListener() {
        serverWideJobHandler = Main.getMain().getServerWideJobHandler();
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        if(event.getPlayer() != null) {
            Player player = event.getPlayer();
            String brokenBlock = event.getBlock().getBlockData().getMaterial().getKey().toString();

            serverWideJobHandler.getPlayerJobsHandler(player.getUniqueId()).addXpToJob(EWayToXP.BREAK, brokenBlock);
        }
    }
}
