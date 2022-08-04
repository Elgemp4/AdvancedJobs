package be.elgem.Gui.Admin;

import be.elgem.Gui.GUI;
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

public class JobSettingsGUI extends GUI {
    Job jobToModify;

    public JobSettingsGUI(Player player, Job jobToModify, GUI previousGUI) {
        super(player, 27, previousGUI);

        this.jobToModify = jobToModify;
    }

    @Override
    protected void createGUI() {
        surroundWith(createItemStack(" ", Material.BLACK_STAINED_GLASS_PANE));

        addItem(9, createItemStack("Changer l'icône du métier", jobToModify.getIcon()), () -> startWaitingForItemSelection("Cliquez sur l'objet que vous voulez définir comme icône", "icon"));
        addItem(10, createItemStack("Changer le nom du métier", Material.NAME_TAG), () -> startWaitingForInput( "Entrez le nouveau nom du métier : ", "name"));
        addItem(11, createItemStack("Changer l'expérience du premier niveau (" + jobToModify.getFirstLevelExperience() + " xp)", Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("Entrez l'expérience du premier niveau : ", "firstLevelExperience"));
        addItem(12, createItemStack("Changer l'expérience de chaque niveau (" + jobToModify.getExperienceGrowth() + " xp)", Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("Entrez l'expérience de chaque niveau : ", "experienceGrowth"));
        addItem(13, createItemStack("Changer le niveau maximum (Niveau " + jobToModify.getMaxLevel() + ")", Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("Entrez le niveau maximum : ", "maxLevel"));
        addItem(14, createItemStack("Supprimer le métier", Material.BARRIER), () -> startWaitingForInput("Vous êtes sur le point de supprimer le métier " + jobToModify.getJobName() + ". Confirmez-vous ? (Oui / Non)", "delete"));

        ItemStack arrow = createItemStack("Retour", Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) arrow.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        arrow.setItemMeta(meta);

        addBackButton();
    }


    public void setNewIcon(Material newIcon) {
        jobToModify.setIcon(newIcon);

        addItem(9, createItemStack("Changer l'icône du métier", newIcon), () -> startWaitingForItemSelection("Cliquez sur l'objet que vous voulez définir comme icône", "icon"));
    }

    @Override
    public void computeSelectedItem(ItemStack item) {
        setNewIcon(item.getType());
    }

    @Override
    public void computeInput(String input) {
        boolean ignoreInterfaceReset = false;

        switch (inputDestination) {
            case "name":
                jobToModify.setJobName(input);
                break;
            case "firstLevelExperience":
                try {
                    jobToModify.setFirstLevelExperience(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Veuillez entrer un nombre correct !");
                }
                break;
            case "experienceGrowth":
                try {
                    jobToModify.setExperienceGrowth(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Veuillez entrer un nombre correct !");
                }
                break;
            case "maxLevel":
                try {
                    jobToModify.setMaxLevel(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Veuillez entrer un nombre correct !");
                }
                break;
            case "delete":
                if(input.equalsIgnoreCase("oui")) {
                    JobEditor.deleteJob(jobToModify.getJobUUID());
                    Main.getMain().getJobsLoader().loadJobs();
                    ignoreInterfaceReset = true;
                    player.sendMessage(ChatColor.RED + "Le métier " + jobToModify.getJobName() + " a été supprimé avec succès !");
                }
                else if(input.equalsIgnoreCase("non")) {
                    player.sendMessage(ChatColor.RED + "Annulation de la suppression du métier");

                }
                else {
                    player.sendMessage(ChatColor.RED + "Veuillez entrer 'oui' ou 'non' : ");
                    startWaitingForInput("Vous êtes sur le point de supprimer le métier " + jobToModify.getJobName() + ". Confirmez-vous ? (Oui / Non)", "delete");
                    ignoreInterfaceReset = true;
                }
        }

        if(!ignoreInterfaceReset) {
            resetAndOpenInventory();
        }
    }

    @Override
    protected String getTitle() {
        return ChatColor.RED + "Paramètres de " + jobToModify.getJobName();
    }
}
