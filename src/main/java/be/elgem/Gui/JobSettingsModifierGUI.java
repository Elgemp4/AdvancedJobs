package be.elgem.Gui;

import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Jobs.JobEditor;
import be.elgem.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class JobSettingsModifierGUI extends GUI{
    Job jobToModify;

    private boolean isChoosingAnItem = false;
    private boolean isInputing = false;
    private String inputDestination = "";

    public JobSettingsModifierGUI(Player player, Job jobToModify) {
        super(player, 27, ChatColor.RED + "Paramètres de " + jobToModify.getJobName());

        this.jobToModify = jobToModify;

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

        addItem(9, createItemStack("Changer l'icône du métier", jobToModify.getIcon()), this::activateItemSelection);
        addItem(10, createItemStack("Changer le nom du métier", Material.NAME_TAG), () -> activateChatInput("name", "Entrez le nouveau nom du métier : "));
        addItem(11, createItemStack("Changer l'expérience du premier niveau (" + jobToModify.getFirstLevelExperience() + " xp)", Material.EXPERIENCE_BOTTLE), () -> activateChatInput("firstLevelExperience", "Entrez l'expérience du premier niveau : "));
        addItem(12, createItemStack("Changer l'expérience de chaque niveau (" + jobToModify.getExperienceGrowth() + " xp)", Material.EXPERIENCE_BOTTLE), () -> activateChatInput("experienceGrowth", "Entrez l'expérience de chaque niveau : "));
        addItem(13, createItemStack("Changer le niveau maximum (Niveau " + jobToModify.getMaxLevel() + ")", Material.EXPERIENCE_BOTTLE), () -> activateChatInput("maxLevel", "Entrez le niveau maximum : "));
        addItem(14, createItemStack("Supprimer le métier", Material.BARRIER), () -> activateChatInput("delete", "Vous êtes sur le point de supprimer le métier " + jobToModify.getJobName() + ". Confirmez-vous ? (Oui / Non)"));

        ItemStack arrow = createItemStack("Retour", Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) arrow.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        arrow.setItemMeta(meta);

        addItem(18, arrow, () -> new ModificationTypeSelector(player, jobToModify).openInventory());
    }

    public boolean isChoosingAnItem() {
        return isChoosingAnItem;
    }

    public boolean isInputing() {
        return isInputing;
    }

    public void setNewIcon(Material newIcon) {
        jobToModify.setIcon(newIcon);

        addItem(9, createItemStack("Changer l'icône du métier", newIcon), this::activateItemSelection);

        isChoosingAnItem = false;
    }


    public void sendInput(String message) {
        boolean ignoreInterfaceReset = false;

        switch (inputDestination) {
            case "name":
                jobToModify.setJobName(message);
                break;
            case "firstLevelExperience":
                try {
                    jobToModify.setFirstLevelExperience(Integer.parseInt(message));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Veuillez entrer un nombre correct !");
                }
                break;
            case "experienceGrowth":
                try {
                    jobToModify.setExperienceGrowth(Integer.parseInt(message));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Veuillez entrer un nombre correct !");
                }
                break;
            case "maxLevel":
                try {
                    jobToModify.setMaxLevel(Integer.parseInt(message));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Veuillez entrer un nombre correct !");
                }
                break;
            case "delete":
                if(message.equalsIgnoreCase("oui")) {
                    JobEditor.deleteJob(jobToModify.getJobUUID());
                    Main.getMain().getJobsLoader().loadJobs();
                    ignoreInterfaceReset = true;
                    player.sendMessage(ChatColor.RED + "Le métier " + jobToModify.getJobName() + " a été supprimé avec succès !");
                }
                else if(message.equalsIgnoreCase("non")) {
                    player.sendMessage(ChatColor.RED + "Annulation de la suppression du métier");

                }
                else {
                    player.sendMessage(ChatColor.RED + "Veuillez entrer 'oui' ou 'non' : ");
                    activateChatInput("delete", "Vous êtes sur le point de supprimer le métier " + jobToModify.getJobName() + ". Confirmez-vous ? (Oui / Non)");
                    ignoreInterfaceReset = true;
                }
        }

        if(!ignoreInterfaceReset) {
            new JobSettingsModifierGUI(player, jobToModify).openInventory();
        }

    }

    private void activateItemSelection() {
        isChoosingAnItem = true;
        player.closeInventory();
        player.sendMessage("[Jobs] Faites un clique droit avec l'objet que vous souhaitez définir comme icône.");
    }

    private void activateChatInput(String destination, String message) {
        player.sendMessage(ChatColor.RED + "[Jobs] " + message);

        isInputing = true;
        inputDestination = destination;
        player.closeInventory();
    }


}
