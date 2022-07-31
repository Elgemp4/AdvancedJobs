package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.EXpMethod;
import be.elgem.Jobs.Misc.ExperienceValues;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class XpStepEditorGui extends GUI {
    private Job jobToEdit;

    private EXpMethod xpMethod;

    private String xpSourceToEdit;

    private int level;

    private ExperienceValues experienceValues;

    public XpStepEditorGui(Player player, Job editedJob, EXpMethod xpMethod, String xpSource, int level) {
        super(player, 27, "Editeur d'xp pour le niveau " + level);

        this.jobToEdit = editedJob;

        this.xpMethod = xpMethod;

        this.xpSourceToEdit = xpSource;

        this.level = level;

        this.experienceValues = jobToEdit.getXpSteps(xpMethod, xpSource).getAmountOfXpPerLevel().get(level);

        createInventory();
    }

    @Override
    protected void createInventory() {
        surroundWith(createItemStack(" ", Material.GREEN_STAINED_GLASS_PANE));

        String parenthesisFixedValue = " (" + (experienceValues.isSingleValue() ? ""+experienceValues.getMin() : "Pas de quantité d'xp fixe") + ")";
        String parenthesisMinValue = " (" + (experienceValues.isSingleValue() ? "Pas de quantité d'xp minimale" : "" + experienceValues.getMin()) + ")";
        String parenthesisMaxValue = " (" + (experienceValues.isSingleValue() ? "Pas de quantité d'xp maximale" : "" + experienceValues.getMax()) + ")";

        addItem(11, createItemStack("Modifier le niveau de l'étape (" + level + ")", Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("[Jobs] Entrez le niveau de l'étape : ", "level"));
        addItem(12, createItemStack("Définir une quantité d'xp fixe" + parenthesisFixedValue, Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("[Jobs] Entrez la quantité d'xp : ", "fixedXp"));
        addItem(14, createItemStack("Définir le minimum d'xp" + parenthesisMinValue, Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("[Jobs] Entrez la quantité minimale d'xp : ", "minXp"));
        addItem(15, createItemStack("Définir le max d'xp" + parenthesisMaxValue, Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("[Jobs] Entrez la quantité maximale d'xp : ", "maxXp"));

        addItem(18, createItemStack("Retour", Material.TIPPED_ARROW), () -> new XpSourceEditor(player, xpMethod, xpSourceToEdit, jobToEdit).openInventory());
    }

    @Override
    protected void computeSelectedItem(ItemStack item) {

    }

    @Override
    protected void computeInput(String input) {
        try{
            int intInput = Integer.parseInt(input);

            switch (inputDestination) {
                case "level":
                    if(jobToEdit.getXpSteps(xpMethod, xpSourceToEdit).getAmountOfXpPerLevel().containsKey(intInput)){
                        player.sendMessage("[Jobs] Ce niveau existe déjà.");
                    }else{
                        jobToEdit.modifyLevelOfXpStep(xpMethod, xpSourceToEdit, level, intInput);
                        this.level = intInput;
                    }
                    break;
                case "fixedXp":
                    jobToEdit.addXpStep(xpMethod, xpSourceToEdit, level, intInput);
                    break;
                case "minXp":
                    jobToEdit.addXpStep(xpMethod, xpSourceToEdit, level, intInput, experienceValues.getMax());
                    break;
                case "maxXp":
                    jobToEdit.addXpStep(xpMethod, xpSourceToEdit, level, experienceValues.getMin(), intInput);
                    break;
            }
            new XpStepEditorGui(player, jobToEdit, xpMethod, xpSourceToEdit, level).openInventory();
        }catch (NumberFormatException e) {
            player.sendMessage("[Jobs] Veuillez entrez un nombre valide.");
        }
    }
}
