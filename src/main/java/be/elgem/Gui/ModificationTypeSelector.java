package be.elgem.Gui;

import be.elgem.Jobs.Jobs.Job;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ModificationTypeSelector extends GUI{
    private Job jobToModify;

    public ModificationTypeSelector(Player player, Job job) {
        super(player, 9, ChatColor.RED + "Choisissez le type de modification");

        this.jobToModify = job;

        createInventory();
    }

    @Override
    protected void createInventory() {
        addItem(3, createItemStack("Paramètres", Material.REDSTONE), () -> new JobSettingsModifierGUI(player, jobToModify).openInventory());
        addItem(5, createItemStack("Changer les moyens de gagner de l'expérience", Material.EXPERIENCE_BOTTLE), () -> new XPModifierGUI(player, jobToModify).openInventory());
    }
}
