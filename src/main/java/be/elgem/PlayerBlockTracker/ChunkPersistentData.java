package be.elgem.PlayerBlockTracker;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
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
        // TODO faire en sorte que les blocs cassés d'une autre manière que par le minage d'un joueur ne soit plus traqué + régler bug lors de pousse bone meal le tronc qui apparâit là où on a bone meal ne donne pas d'xp
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
            blockLocationArrayList.add(ChunkUtils.getLocationFromInt(chunk.getWorld(), chunk, blockLocation));
        }
    }

    public void savePlayersBlockIntoChunkData() {
        int[] intLocations = new int[blockLocationArrayList.size()];

        for (int i = 0; i < blockLocationArrayList.size(); i++) {
            intLocations[i] = ChunkUtils.getIntFromLocation(blockLocationArrayList.get(i));
        }

        this.persistentDataContainer.set(playerBlocks, PersistentDataType.INTEGER_ARRAY, intLocations);
    }

}
