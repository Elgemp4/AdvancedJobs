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

public class ChooseModificationGUI extends GUI {
    private Job jobToModify;

    public ChooseModificationGUI(Player player, Job job) {
        super(player, 27, ChatColor.RED + "Choisissez le type de modification");

        this.jobToModify = job;

        createInventory();
    }

    @Override
    protected void createInventory() {
        surroundWith(createItemStack(" ", Material.BLACK_STAINED_GLASS_PANE));

        addItem(12, createItemStack("Paramètres", Material.REDSTONE), () -> new JobSettingsGUI(player, jobToModify).openInventory());
        addItem(14, createItemStack("Changer les moyens de gagner de l'expérience", Material.EXPERIENCE_BOTTLE), () -> new ChooseXpMethod(player, jobToModify).openInventory());

        ItemStack arrow = createItemStack("Retour", Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) arrow.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        arrow.setItemMeta(meta);

        addItem(18, arrow, () -> new ChooseJobToEditGUI(player).openInventory());
    }

    @Override
    public void computeSelectedItem(ItemStack item) {

    }

    @Override
    public void computeInput(String input) {

    }
}
