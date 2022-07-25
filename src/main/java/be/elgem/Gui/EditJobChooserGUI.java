package be.elgem.Gui;

import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Jobs.JobEditor;
import be.elgem.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class EditJobChooserGUI extends GUI{
    public EditJobChooserGUI(Player player) {
        super(player, 27, ChatColor.RED + "Choisissez le métier à modifier");

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

        ArrayList<Job> jobs = Main.getMain().getJobsLoader().getJobsArray();

        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            addItem(9 + i, createItemStack(job.getJobName(), job.getIcon()), () -> {
                new ModificationTypeSelector(player, job).openInventory();
            });
        }

        addItem(9 + jobs.size(), createItemStack("Créer un nouveau métier", Material.PAPER), this::createJob);
    }

    public void createJob() {
        UUID uuid = UUID.randomUUID();
        Material icon = Material.DIRT;
        String name = "Nouveau métier";
        int maxLevel = 200;
        int firstLevelExperience = 2000;
        int experienceGrowth = 600;

        JobEditor.createJob(uuid, name, icon, maxLevel, firstLevelExperience, experienceGrowth);

        Main.getMain().getJobsLoader().loadJobs();

        player.closeInventory();

        new EditJobChooserGUI(player).openInventory();
    }
}
