package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.ExperienceValues;
import be.elgem.Jobs.Misc.XpSteps;
import be.elgem.Jobs.Misc.EXpMethod;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class XpSourceEditor extends GUI {
    private EXpMethod xpMethod;

    private Job jobToModify;

    private String xpSourceToEdit;

    public XpSourceEditor(Player player, EXpMethod wayToXP, String xpSource, Job editedJob) {
        super(player, 27, "Editeur d'xp pour " + xpSource);

        this.xpMethod = wayToXP;
        
        this.xpSourceToEdit = xpSource;

        this.player = player;

        this.jobToModify = editedJob;
    }

    @Override
    protected void createInventory() {
        surroundWith(createItemStack(" ", Material.GREEN_STAINED_GLASS_PANE));

        ItemStack amountOfXpList = createItemStack("Liste des quantités d'xp par niveau", Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = amountOfXpList.getItemMeta();

        ArrayList<String> lore = createXpSourceLore(jobToModify, xpMethod, xpSourceToEdit);

        meta.setLore(lore);

        amountOfXpList.setItemMeta(meta);
        addItem(4, amountOfXpList, null);

        addItem(10, createSkull("Retire une étape", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0="),
                () -> startWaitingForInput("[Jobs] Entrez l'étape que vous souhaitez retirer : ", "xpStepToRemove"));

        addItem(13, createItemStack("Modifier une étape", Material.REPEATER),
                () -> startWaitingForInput("[Jobs] Entrez le niveau de l'étape que vous souhaitez modifier : ", "levelToModify"));

        addItem(16, createSkull("Ajouter une étape d'xp", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZmMzE0MzFkNjQ1ODdmZjZlZjk4YzA2NzU4MTA2ODFmOGMxM2JmOTZmNTFkOWNiMDdlZDc4NTJiMmZmZDEifX19"),
                () -> startWaitingForInput("[Jobs] Entrez l'étape que vous souhaitez ajouter : ", "xpStepToAdd"));

        addItem(18, createItemStack("Retour", Material.TIPPED_ARROW), () -> new BreakGUI(player, jobToModify).openInventory());
        addItem(26, createItemStack("Supprimer la source d'expérience", Material.BARRIER), () -> {
            jobToModify.removeXpSource(xpMethod, xpSourceToEdit);
            new BreakGUI(player, jobToModify).openInventory();
        });

    }

    @Override
    public void computeSelectedItem(ItemStack item) {

    }

    @Override
    public void computeInput(String input) {
        try{
            int intInput = Integer.parseInt(input);

            switch (inputDestination) {
                case "xpStepToRemove":
                    if(!jobToModify.getXpSteps(xpMethod, xpSourceToEdit).getAmountOfXpPerLevel().containsKey(intInput)) {
                        player.sendMessage("[Jobs] L'étape n'existe pas.");

                    } else {
                        jobToModify.removeXpStep(xpMethod, xpSourceToEdit, intInput);
                        new XpSourceEditor(player, xpMethod, xpSourceToEdit, jobToModify).openInventory();
                    }

                    break;
                case "levelToModify":
                    if(jobToModify.getXpSteps(xpMethod, xpSourceToEdit).getAmountOfXpPerLevel().containsKey(intInput)) {
                        new XpStepEditorGui(player, jobToModify, xpMethod, xpSourceToEdit, intInput).openInventory();

                    } else {
                        player.sendMessage("[Jobs] L'étape n'existe pas.");
                    }

                    break;
                case "xpStepToAdd":
                    if(jobToModify.getXpSteps(xpMethod, xpSourceToEdit).getAmountOfXpPerLevel().containsKey(intInput)) {
                        player.sendMessage("[Jobs] L'étape existe déjà.");

                    } else {
                        jobToModify.addXpStep(xpMethod, xpSourceToEdit, intInput, 0);
                        new XpStepEditorGui(player, jobToModify, xpMethod, xpSourceToEdit, intInput).openInventory();
                    }

                    break;
            }
        }
        catch(NumberFormatException e){
            player.sendMessage("[Jobs] Vous devez entrer un nombre valide !");
        }

    }

    public static ArrayList<String> createXpSourceLore(Job job, EXpMethod xpMethod, String xpSource) {
        ArrayList<String> lore = new ArrayList<>();

        XpSteps xpSteps = job.getXpSteps(xpMethod, xpSource);

        if(xpSteps == null) {
            lore.add("Aucun niveau n'a été défini");
        } else {
            for (int level : xpSteps.getAmountOfXpPerLevel().keySet()) {
                ExperienceValues experienceValues = xpSteps.getAmountOfXpPerLevel().get(level);
                if(experienceValues.isSingleValue()){
                    lore.add(ChatColor.GRAY + "Niveau " + level + " : " + ChatColor.GOLD + xpSteps.getAmountOfXp(level) + " xp");
                }
                else {
                    lore.add(ChatColor.GRAY + "Niveau " + level + " : " + ChatColor.GOLD + experienceValues.getMin() + " à " + experienceValues.getMax() + " xp");
                }

            }
        }

        return lore;
    }
}
