package be.elgem.Gui.Admin;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ItemSelect {
    private Player player;

    private boolean isWaiting = false;


    public void startListening() {
        isWaiting = true;
        player.closeInventory();
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public abstract void getResult(ItemStack item);
}
