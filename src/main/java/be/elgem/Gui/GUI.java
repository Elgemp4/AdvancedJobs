package be.elgem.Gui;

import be.elgem.Main;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Skull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;


public abstract class GUI {
    protected Player player;

    protected Inventory menu;

    protected HashMap<Integer, Runnable> actionsForItems;

    protected boolean isWaitingForInput = false;
    protected String inputDestination = "";

    protected boolean isWaitingForItemSelection = false;
    protected String itemSelectionDestination = "";

    public GUI(Player player, int size, String title) {
        this.player = player;

        actionsForItems = new HashMap<>();

        menu = Bukkit.createInventory(player, size, title);
    }

    public static ItemStack createItemStack(Material material) {
        return new ItemStack(material);
    }

    public static ItemStack createItemStack(String name, Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + name);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack createSkull(String name, String value) {

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + name);
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", value));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }

        head.setItemMeta(skullMeta);
        return head;
    }

    public void addItem(int slot, ItemStack itemStack, Runnable actionOnClick){
        menu.setItem(slot, itemStack);

        actionsForItems.put(slot, actionOnClick);
    }

    public void executeActionForClick(int slot) {
        if(actionsForItems.containsKey(slot)){
            if(actionsForItems.get(slot) != null) {
                actionsForItems.get(slot).run();
            }
        }
    }

    public void openInventory() {
        menu.clear();
        createInventory();
        Main.getMain().getOpenedGUI().addGUI(player, this);
        player.openInventory(menu);
    }

    protected void surroundWith(ItemStack surroundItem) {
        for (int i = 0; i < 9; i++) {
            addItem(i, surroundItem, null);
        }

        for (int i = menu.getSize() - 9; i < menu.getSize(); i++) {
            addItem(i, surroundItem, null);
        }
    }

    protected void clearActions() {
        actionsForItems.clear();
    }

    public Inventory getMenu() {
        return menu;
    }

    /**
     * Start waiting for the player input
     * @param message The message to display to the player
     * @param destination An identifier to know to what the input is bound to
     */
    protected void startWaitingForInput(String message, String destination) {
        isWaitingForInput = true;
        inputDestination = destination;

        if(!message.equals("")) {
            player.sendMessage(message);
        }

        player.closeInventory();
    }

    /**
     * Start waiting for the player to choose an item
     * @param message The message to display to the player
     * @param destination An identifier to know to what the item is bound to
     */
    protected void startWaitingForItemSelection(String message, String destination) {
        isWaitingForItemSelection = true;
        itemSelectionDestination = destination;

        if(!message.equals("")) {
            player.sendMessage(message);
        }
        player.closeInventory();
    }


    public boolean isWaitingForInput() {
        return isWaitingForInput;
    }

    public boolean isWaitingForItemSelection() {
        return isWaitingForItemSelection;
    }

    protected abstract void createInventory();

    public void getInput(String input) {
        isWaitingForInput = false;
        this.openInventory();
        computeInput(input);
    }

    public void getItemSelection(ItemStack itemStack) {
        isWaitingForItemSelection = false;
        this.openInventory();
        computeSelectedItem(itemStack);
    }

    protected abstract void computeSelectedItem(ItemStack item);

    protected abstract void computeInput(String input);
}
