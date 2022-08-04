package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.EXpMethod;
import be.elgem.Jobs.Misc.ExperienceValues;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class XpStepEditorGui extends GUI {
    final private Job jobToEdit;

    final private EXpMethod xpMethod;

    final private String xpSourceToEdit;

    private int level;

    private int fixedXp;

    private int minXp;

    private int maxXp;

    final private ExperienceValues experienceValues;

    public XpStepEditorGui(Player player, Job editedJob, EXpMethod xpMethod, String xpSource, int level, GUI previousGUI) {
        super(player, 27, previousGUI);

        this.jobToEdit = editedJob;

        this.xpMethod = xpMethod;

        this.xpSourceToEdit = xpSource;

        this.experienceValues = jobToEdit.getXpSteps(xpMethod, xpSource).getAmountOfXpPerLevel().get(level);

        this.level = level;

        this.fixedXp = experienceValues.isSingleValue() ? experienceValues.getMin() : 0;

        this.minXp = experienceValues.isSingleValue() ? 0 : experienceValues.getMin();

        this.maxXp = experienceValues.isSingleValue() ? 0 : experienceValues.getMax();
    }

    @Override
    protected void createGUI() {
        surroundWith(createItemStack(" ", Material.GREEN_STAINED_GLASS_PANE));

        String parenthesisFixedValue = " (" + (fixedXp == 0 ? "Pas de quantité d'xp fixe" : fixedXp) + ")";
        String parenthesisMinValue = " (" + (minXp == 0 ? "Pas de quantité d'xp minimale" : minXp) + ")";
        String parenthesisMaxValue = " (" + (maxXp == 0 ? "Pas de quantité d'xp maximale" : maxXp) + ")";

        addItem(11, createItemStack("Modifier le niveau de l'étape (" + level + ")", Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("[Jobs] Entrez le niveau de l'étape : ", "level"));
        addItem(12, createItemStack("Définir une quantité d'xp fixe" + parenthesisFixedValue, Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("[Jobs] Entrez la quantité d'xp : ", "fixedXp"));
        addItem(14, createItemStack("Définir le minimum d'xp" + parenthesisMinValue, Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("[Jobs] Entrez la quantité minimale d'xp : ", "minXp"));
        addItem(15, createItemStack("Définir le max d'xp" + parenthesisMaxValue, Material.EXPERIENCE_BOTTLE), () -> startWaitingForInput("[Jobs] Entrez la quantité maximale d'xp : ", "maxXp"));

        addBackButton();
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
                    this.fixedXp = intInput;
                    this.minXp = 0;
                    this.maxXp = 0;

                    break;
                case "minXp":
                    jobToEdit.addXpStep(xpMethod, xpSourceToEdit, level, intInput, experienceValues.getMax());
                    this.maxXp = intInput;
                    this.fixedXp = 0;

                    break;
                case "maxXp":
                    jobToEdit.addXpStep(xpMethod, xpSourceToEdit, level, experienceValues.getMin(), intInput);
                    this.minXp = intInput;
                    this.fixedXp = 0;

                    break;
            }

            resetAndOpenInventory();
        }catch (NumberFormatException e) {
            player.sendMessage("[Jobs] Veuillez entrez un nombre valide.");
        }
    }

    @Override
    protected String getTitle() {
        return "Editeur d'xp pour le niveau " + level;
    }
}
