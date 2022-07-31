package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Gui.Admin.ChooseXpMethod;
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

    public XPEditorInsertGUI(Player player, EXpMethod wayToXP, Job editedJob) {
        super(player, 54, "Editeur de l'action " + wayToXP.toString());

        this.xpMethod = wayToXP;

        this.jobToModify = editedJob;

        createInventory();
    }

    @Override
    protected void createInventory() {
        surroundWith(new ItemStack(Material.GREEN_STAINED_GLASS_PANE));

        addItem(4, createItemStack("Ajouter moyen d'xp", Material.HOPPER), () -> startWaitingForItemSelection("[Jobs] Faites un clique droit avec l'object que vous voulez ajouter : ", "newXpSource"));

        addItem(45, createItemStack("Retour", Material.TIPPED_ARROW), () -> new ChooseXpMethod(player, jobToModify).openInventory());

        HashMap<ItemStack, String> xpSource = loadXpSources();



        Object[] items = xpSource.keySet().toArray();

        for (int i = 0; i < xpSource.size(); i++) {
            ItemStack itemStack = (ItemStack) items[i];
            String xpSourceString = xpSource.get(itemStack);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(XpSourceEditor.createXpSourceLore(jobToModify, xpMethod, xpSourceString));
            itemStack.setItemMeta(meta);

            addItem(i + 9, itemStack, () -> new XpSourceEditor(player, xpMethod, xpSourceString, jobToModify).openInventory());
        }
    }

    protected abstract boolean isAValidXpSource(Object xpSource);

    protected abstract HashMap<ItemStack, String> loadXpSources();


    @Override
    public void computeSelectedItem(ItemStack item) {
        switch (itemSelectionDestination) {
            case "newXpSource":
                if (isAValidXpSource(item)) {
                    jobToModify.addXpSource(xpMethod, item.getType().toString(), null);
                    openInventory();
                } else {
                    player.sendMessage("[Jobs] Ce n'est pas un moyen d'xp valide.");
                }
                break;
        }
    }

    @Override
    public void computeInput(String input) {

    }
}
