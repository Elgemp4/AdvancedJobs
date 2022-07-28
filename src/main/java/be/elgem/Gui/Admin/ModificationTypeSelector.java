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

public class ModificationTypeSelector extends GUI {
    private Job jobToModify;

    public ModificationTypeSelector(Player player, Job job) {
        super(player, 27, ChatColor.RED + "Choisissez le type de modification");

        this.jobToModify = job;

        createInventory();
    }

    @Override
    protected void createInventory() {
        ItemStack blackGlassPane = createItemStack(" ", Material.BLACK_STAINED_GLASS_PANE);;

        for (int row = 0; row <= 2; row+=2) {
            for (int col = 0; col < 9; col++) {
                addItem(row*9 + col, blackGlassPane, null);
            }
        }

        addItem(12, createItemStack("Paramètres", Material.REDSTONE), () -> new JobSettingsModifierGUI(player, jobToModify).openInventory());
        addItem(14, createItemStack("Changer les moyens de gagner de l'expérience", Material.EXPERIENCE_BOTTLE), () -> new WayToXpChooserGUI(player, jobToModify).openInventory());

        ItemStack arrow = createItemStack("Retour", Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) arrow.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        arrow.setItemMeta(meta);

        addItem(18, arrow, () -> new EditJobChooserGUI(player).openInventory());
    }

    @Override
    public void computeSelectedItem(ItemStack item) {

    }

    @Override
    public void computeInput(String input) {

    }
}
