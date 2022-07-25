package be.elgem.PlayerBlockTracker;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

public class ChunkUtils {
    public static Location getLocationFromInt (World world, Chunk chunk, int location) {
        int chunkX = (location>>24) & 0xF;
        int chunkZ = (location>>16) & 0xF;
        int chunkY = (location & 0x00FF);

        chunkX += chunk.getX() * 16;
        chunkZ += chunk.getZ() * 16;

        return new Location(world, chunkX, chunkY, chunkZ);
    }

    public static int getIntFromLocation(Location location) {
        int chunkX = ((location.getBlockX() % 16) + 16) % 16; //Pour les chunks négatifs
        int chunkZ = ((location.getBlockZ() % 16) + 16) % 16;
        int chunkY = location.getBlockY();

        return (chunkX<<24) | (chunkZ<<16) | (chunkY);
    }
}
