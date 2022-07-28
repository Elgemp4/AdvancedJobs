package be.elgem.Gui.Admin;

import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class WayToXpChooserGUI extends GUI {
    Job jobToModify;

    public WayToXpChooserGUI(Player player, Job jobToModify) {
        super(player, 54, ChatColor.RED + "Choisissez l'action à modifier");

        this.jobToModify = jobToModify;

        createInventory();
    }

    @Override
    protected void createInventory() {
        ItemStack greenGlassPane = createItemStack(" ", Material.GREEN_STAINED_GLASS_PANE);;
        for (int i = 0; i < 54; i++) {
            addItem(i, greenGlassPane, null);
        }

        addItem(11, createItemStack("Casser", Material.NETHERITE_PICKAXE), null);
        addItem(12, createItemStack("Tuer", Material.NETHERITE_SWORD), null);
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

        ItemStack arrow = createItemStack("Retour", Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) arrow.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        arrow.setItemMeta(meta);

        addItem(45, arrow, () -> new ModificationTypeSelector(player, jobToModify).openInventory());
    }

    @Override
    public void computeSelectedItem(ItemStack item) {

    }

    @Override
    public void computeInput(String input) {

    }
}
