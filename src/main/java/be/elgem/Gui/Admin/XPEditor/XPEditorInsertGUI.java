package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.EXpMethod;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;


public abstract class XPEditorInsertGUI extends GUI {

    protected EXpMethod xpMethod;

    protected Job jobToModify;

    public XPEditorInsertGUI(Player player, EXpMethod xpMethod, Job editedJob, GUI previousGUI) {
        super(player, 54, previousGUI);

        this.xpMethod = xpMethod;

        this.jobToModify = editedJob;
    }

    @Override
    protected void createGUI() {
        surroundWith(new ItemStack(Material.GREEN_STAINED_GLASS_PANE));

        addItem(4, createItemStack("Ajouter moyen d'xp", Material.HOPPER), () -> startWaitingForItemSelection("[Jobs] Faites un clique droit avec l'object que vous voulez ajouter : ", "newXpSource"));

        HashMap<ItemStack, String> xpSource = loadXpSources();

        Object[] items = xpSource.keySet().toArray();

        for (int i = 0; i < xpSource.size(); i++) {
            ItemStack itemStack = (ItemStack) items[i];
            String xpSourceString = xpSource.get(itemStack);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(XpSourceEditor.createXpSourceLore(jobToModify, xpMethod, xpSourceString));
            itemStack.setItemMeta(meta);

            addItem(i + 9, itemStack, () -> new XpSourceEditor(player, xpMethod, xpSourceString, jobToModify, this).openInventory());
        }

        addBackButton();
    }

    protected abstract String getXpSourceFromItemStack(ItemStack itemStack);

    protected abstract HashMap<ItemStack, String> loadXpSources();

    @Override
    protected String getTitle() {
        return "Editeur de l'action " + xpMethod.toString();
    }

    @Override
    public void computeSelectedItem(ItemStack item) {
        String xpSource = getXpSourceFromItemStack(item);
        if (xpSource != null) {
            jobToModify.addXpSource(xpMethod, xpSource, null);
            resetAndOpenInventory();
        } else {
            player.sendMessage("[Jobs] Ce n'est pas un moyen d'xp valide.");
        }
    }

    @Override
    public void computeInput(String input) {

    }
}
