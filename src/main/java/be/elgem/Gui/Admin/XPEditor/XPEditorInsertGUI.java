package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Gui.Admin.ModificationTypeSelector;
import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.EWayToXP;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;


public abstract class XPEditorInsertGUI extends GUI {
    protected Player player;

    protected EWayToXP wayToXP;

    protected Job jobToModify;

    public XPEditorInsertGUI(Player player, EWayToXP wayToXP, Job editedJob) {
        super(player, 54, "Editeur de l'action " + wayToXP.toString());

        this.player = player;

        this.jobToModify = editedJob;
    }

    @Override
    protected void createInventory() {
        for (int i = 0; i < 9; i++) {
            addItem(i, createItemStack(" ", Material.GREEN_STAINED_GLASS_PANE), null);
        }

        for (int i = 48; i < 54; i++) {
            addItem(i, createItemStack(" ", Material.GREEN_STAINED_GLASS_PANE), null);
        }

        addItem(4, createItemStack("Ajouter moyen d'xp", Material.HOPPER), () -> startWaitingForItemSelection("[Jobs] Faites un clique droit avec l'object que vous voulez ajouter : ", "newXpSource"));

        addItem(48, createItemStack("Retour", Material.TIPPED_ARROW), () -> new ModificationTypeSelector(player, jobToModify).openInventory());

        HashMap<ItemStack, String> actions = loadAction();


        ItemStack[] items = (ItemStack[]) actions.keySet().toArray();
        for (int i = 0; i < actions.size(); i++) {
            ItemStack itemStack = items[i];

            addItem(i + 9, itemStack, () -> {
                String actionToEdit = actions.get(itemStack);

                new WayToXPEditor(player, wayToXP, actionToEdit, jobToModify).openInventory();
            });
        }
    }

    protected abstract void isAValidAction();

    protected abstract HashMap<ItemStack, String> loadAction();
}
