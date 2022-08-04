package be.elgem.Gui.Admin;

import be.elgem.Gui.Admin.XPEditor.BreakGUI;
import be.elgem.Gui.Admin.XPEditor.KillGUI;
import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChooseXpMethod extends GUI {
    Job jobToModify;

    public ChooseXpMethod(Player player, Job jobToModify, GUI previousGUI) {
        super(player, 54, previousGUI);

        this.jobToModify = jobToModify;
    }

    @Override
    protected void createGUI() {
        ItemStack greenGlassPane = createItemStack(" ", Material.GREEN_STAINED_GLASS_PANE);
        for (int i = 0; i < 54; i++) {
            addItem(i, greenGlassPane, null);
        }

        addItem(11, createItemStack("Casser", Material.NETHERITE_PICKAXE), () -> new BreakGUI(player, jobToModify, this).openInventory());
        addItem(12, createItemStack("Tuer", Material.NETHERITE_SWORD), () -> new KillGUI(player, jobToModify, this).openInventory());
        addItem(13, createItemStack("Pêcher", Material.FISHING_ROD), null);
        addItem(14, createItemStack("Tondre", Material.SHEARS), null);
        addItem(15, createItemStack("Récupérer du lait", Material.MILK_BUCKET), null);

        addItem(29, createItemStack("Fabriquer", Material.CRAFTING_TABLE), null);
        addItem(30, createItemStack("Cuire", Material.FURNACE), null);
        addItem(31, createItemStack("Préparer des potions", Material.BREWING_STAND), null);
        addItem(32, createItemStack("Enchanter", Material.ENCHANTING_TABLE), null);
        addItem(33, createItemStack("Réparer", Material.ANVIL), null);

        addItem(47, createItemStack("Manger", Material.COOKED_PORKCHOP), null);
        addItem(48, createItemStack("Élever", Material.WHEAT), null);
        addItem(50, createItemStack("Échanger avec des villageois", Material.EMERALD), null);
        addItem(51, createItemStack("Collecter", Material.HONEYCOMB), null);

        addBackButton();
    }

    @Override
    public void computeSelectedItem(ItemStack item) {

    }

    @Override
    public void computeInput(String input) {

    }

    @Override
    protected String getTitle() {
        return ChatColor.RED + "Choisissez l'action à modifier";
    }
}
