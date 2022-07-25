package be.elgem.Listeners;

import be.elgem.PlayerBlockTracker.ChunkPersistentData;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.HashMap;

public class ChunkLoadingListener implements Listener {
    private static ChunkLoadingListener thisClass;

    private HashMap<Chunk, ChunkPersistentData> chunkPersistentDataHashMap;

    public ChunkLoadingListener() {
        ChunkLoadingListener.thisClass = this;

        chunkPersistentDataHashMap = new HashMap<>();
    }

    @EventHandler
    public void onLoad(ChunkLoadEvent event){
        chunkPersistentDataHashMap.put(event.getChunk(), new ChunkPersistentData(event.getChunk()));
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent event){
        chunkPersistentDataHashMap.get(event.getChunk()).savePlayersBlockIntoChunkData();

        chunkPersistentDataHashMap.remove(event.getChunk());
    }

    public void loadChunks(Chunk[] loadedChunks) {
        for (Chunk chunk : loadedChunks) {
            chunkPersistentDataHashMap.put(chunk, new ChunkPersistentData(chunk));
        }
    }

    public void saveAllChunks() {
        for (ChunkPersistentData chunkPersistentData : chunkPersistentDataHashMap.values()) {
            chunkPersistentData.savePlayersBlockIntoChunkData();
        }
    }

    public ChunkPersistentData getChunkPersistentData (Chunk chunk) {
        return chunkPersistentDataHashMap.get(chunk);
    }



    public static ChunkLoadingListener getChunkLoadingListener() {
        return thisClass;
    }
}
