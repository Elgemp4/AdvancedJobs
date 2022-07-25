package be.elgem.Gui;

import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Jobs.JobEditor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JobSettingsModifierGUI extends GUI{
    Job jobToModify;

    private boolean isChoosingAnItem = false;

    public JobSettingsModifierGUI(Player player, Job jobToModify) {
        super(player, 9, ChatColor.RED + "Paramètres de " + jobToModify.getJobName());

        this.jobToModify = jobToModify;

        createInventory();
    }

    @Override
    protected void createInventory() {
        addItem(0, createItemStack("Changer l'icône du métier", jobToModify.getDisplayItem().getType()), this::activateItemSelection);
    }

    public boolean isChoosingAnItem() {
        return isChoosingAnItem;
    }

    public void setNewIcon(Material newIcon) {
        jobToModify.setDisplayItem(newIcon);

        addItem(0, createItemStack("Changer l'icône du métier", newIcon), this::activateItemSelection);

        isChoosingAnItem = false;
    }

    private void activateItemSelection() {
        isChoosingAnItem = true;
        player.closeInventory();
        player.sendMessage("[Jobs] Faites un clique droit avec l'objet que vous souhaitez définir comme icône.");
    }
}
