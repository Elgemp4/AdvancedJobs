package be.elgem.Gui;

import be.elgem.Jobs.Jobs.Job;
import be.elgem.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditJobChooserGUI extends GUI{
    public EditJobChooserGUI(Player player) {
        super(player, 27, ChatColor.RED + "Choisissez le métier à modifier");

        createInventory();
    }

    @Override
    protected void createInventory() {
        ItemStack blackGlassPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackGlassPaneMeta = blackGlassPane.getItemMeta();
        blackGlassPaneMeta.setDisplayName(" ");
        blackGlassPane.setItemMeta(blackGlassPaneMeta);

        for (int row = 0; row <= 2; row+=2) {
            for (int col = 0; col < 9; col++) {
                addItem(row*9 + col, blackGlassPane, null);
            }
        }

        int jobCpt = 0;
        for (Job job : Main.getMain().getJobsLoader().getJobsArray()) {
            addItem(9 + jobCpt, job.getDisplayItem(), () -> {
                new ModificationTypeSelector(player, job).openInventory();
            });
        }
    }
}
