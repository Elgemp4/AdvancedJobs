package be.elgem.PlayerBlockTracker;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ChunkPersistentData {
    private PersistentDataContainer persistentDataContainer;

    private Chunk chunk;

    private final NamespacedKey playerBlocks = NamespacedKey.minecraft("players_blocks");

    private ArrayList<Location> blockLocationArrayList;

    public ChunkPersistentData(Chunk chunk) {
        this.chunk = chunk;

        this.persistentDataContainer = chunk.getPersistentDataContainer();
        this.blockLocationArrayList = new ArrayList<>();

        loadPlayerPlacedBlock();
    }

    public boolean isAPlayerBlock(Block block) {
        return blockLocationArrayList.contains(block.getLocation());
    }

    public void addPlayerBlock(Block block) {
        blockLocationArrayList.add(block.getLocation());
    }

    public void removePlayerBlock(Block block) {
        blockLocationArrayList.remove(block.getLocation());
    }

    private void loadPlayerPlacedBlock() {
        int[] location = persistentDataContainer.get(playerBlocks, PersistentDataType.INTEGER_ARRAY);

        if (location==null) {
            return;
        }

        blockLocationArrayList = new ArrayList<>();

        for (int blockLocation : location) {
            blockLocationArrayList.add(intToLocation(chunk.getWorld(), blockLocation));
        }
    }

    public void savePlayersBlockIntoChunkData() {
        int[] intLocations = new int[blockLocationArrayList.size()];

        for (int i = 0; i < blockLocationArrayList.size(); i++) {
            intLocations[i] = locationToInt(blockLocationArrayList.get(i));
        }

        this.persistentDataContainer.set(playerBlocks, PersistentDataType.INTEGER_ARRAY, intLocations);
    }

    private int locationToInt(Location location) { //Fonctionne
        int chunkX = ((location.getBlockX() % 16) + 16) % 16; //Pour les chunks négatifs
        int chunkZ = ((location.getBlockZ() % 16) + 16) % 16;
        int chunkY = location.getBlockY();

        return (chunkX<<24) | (chunkZ<<16) | (chunkY);
    }

    private Location intToLocation(World world, int location) {
        int chunkX = (location>>24) & 0xF;
        int chunkZ = (location>>16) & 0xF;
        int chunkY = (location & 0x00FF);

        chunkX += chunk.getX() * 16;
        chunkZ += chunk.getZ() * 16;

        return new Location(world, chunkX, chunkY, chunkZ);
    }
}
