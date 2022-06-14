package be.elgem.Listeners;

import be.elgem.Jobs.Misc.EWayToXP;
import be.elgem.Jobs.Player.ServerWideJobHandler;
import be.elgem.Main;
import be.elgem.PlayerBlockTracker.ChunkPersistentData;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockBreakListener implements Listener {
    private ServerWideJobHandler serverWideJobHandler;

    private ChunkLoadingListener chunkLoadingListener;

    public BlockBreakListener() {
        this.serverWideJobHandler = Main.getMain().getServerWideJobHandler();
        this.chunkLoadingListener = ChunkLoadingListener.getChunkLoadingListener();
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Block brokenBlock = event.getBlock();

        ChunkPersistentData chunkPersistentData = chunkLoadingListener.getChunkPersistentData(event.getBlock().getChunk());

        if(!ListenerManager.canGainExperience(event.getPlayer())) {
            return;
        }

        if (chunkPersistentData.isAPlayerBlock(brokenBlock)){
            chunkPersistentData.removePlayerBlock(brokenBlock);
            return;
        }

        String brokenBlockString = brokenBlock.getBlockData().getMaterial().getKey().toString();
        Player player = event.getPlayer();

        serverWideJobHandler.getPlayerJobsHandler(player.getUniqueId()).addXpToJob(EWayToXP.BREAK, brokenBlockString);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!ListenerManager.canGainExperience(event.getPlayer())) {
            return;
        }

        Player player = event.getPlayer();
        Block placedBlock = event.getBlock();

        ChunkPersistentData chunkPersistentData = chunkLoadingListener.getChunkPersistentData(placedBlock.getChunk());

        chunkPersistentData.addPlayerBlock(placedBlock);

        String placeBlockString = event.getBlock().getBlockData().getMaterial().getKey().toString();

        serverWideJobHandler.getPlayerJobsHandler(player.getUniqueId()).addXpToJob(EWayToXP.PLACE, placeBlockString);
    }

}
