package be.elgem.Gui.Admin;

import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Jobs.JobEditor;
import be.elgem.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class ChooseJobToEditGUI extends GUI {
    private int numberOfPages = 1;
    private int currentPage = 0;

    public ChooseJobToEditGUI(Player player) {
        super(player, 27, ChatColor.RED + "Choisissez le métier à modifier");

        createInventory();
    }

    @Override
    protected void createInventory() {
        this.menu.clear();
        this.clearActions();

        surroundWith(new ItemStack(Material.BLACK_STAINED_GLASS_PANE));

        ArrayList<Job> jobs = Main.getMain().getJobsLoader().getJobsArray();

        numberOfPages = (int) Math.ceil((jobs.size() + 1) / 9.0); //+1 is for the job create button

        int firstDisplayedJob = currentPage * 9;
        int lastDisplayedJob = Math.min(firstDisplayedJob + 9, jobs.size());

        for (int i = firstDisplayedJob; i < lastDisplayedJob; i++) {
            Job job = jobs.get(i);

            int index = 9 + (i % 9);

            addItem(index, createItemStack(job.getJobName(), job.getIcon()), () -> new ChooseModificationGUI(player, job).openInventory());
        }

        if(lastDisplayedJob == jobs.size()) {
            int index = 9 + (lastDisplayedJob % 9);

            if(menu.getItem(index) == null){
                addItem( index, createItemStack("Créer un métier", Material.PAPER), this::createJob);
            }
        }

        if(numberOfPages > 1) {
            if(currentPage > 0) {
                addItem(20, createItemStack("Page précédente", Material.ARROW), this::previousPage);
            }

            addItem(22, createItemStack("Page " + (currentPage + 1) + "/" + (numberOfPages), Material.PAPER), null);

            if(currentPage + 1 != numberOfPages) {
                addItem(24, createItemStack("Page suivante", Material.ARROW), this::nextPage);
            }
        }
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

        this.createInventory();
    }

    private void nextPage() {
        currentPage++;
        createInventory();
    }

    private void previousPage() {
        currentPage--;
        createInventory();
    }

    @Override
    public void computeSelectedItem(ItemStack item) {

    }

    @Override
    public void computeInput(String input) {

    }
}
