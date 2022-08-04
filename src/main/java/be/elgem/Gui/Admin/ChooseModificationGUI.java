package be.elgem.Gui.Admin;

import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChooseModificationGUI extends GUI {
    final private Job jobToModify;

    public ChooseModificationGUI(Player player, Job job, GUI previousGUI) {
        super(player, 27,  previousGUI);

        this.jobToModify = job;
    }

    @Override
    protected void createGUI() {
        surroundWith(createItemStack(" ", Material.BLACK_STAINED_GLASS_PANE));

        addItem(12, createItemStack("Paramètres", Material.REDSTONE), () -> new JobSettingsGUI(player, jobToModify, this).openInventory());
        addItem(14, createItemStack("Changer les moyens de gagner de l'expérience", Material.EXPERIENCE_BOTTLE), () -> new ChooseXpMethod(player, jobToModify, this).openInventory());

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
        return ChatColor.RED + "Choisissez le type de modification";
    }
}
