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

        this.wayToXP = wayToXP;

        this.player = player;

        this.jobToModify = editedJob;

        createInventory();
    }

    @Override
    protected void createInventory() {
        for (int i = 0; i < 9; i++) {
            addItem(i, createItemStack(" ", Material.GREEN_STAINED_GLASS_PANE), null);
        }

        for (int i = 45; i < 54; i++) {
            addItem(i, createItemStack(" ", Material.GREEN_STAINED_GLASS_PANE), null);
        }

        addItem(4, createItemStack("Ajouter moyen d'xp", Material.HOPPER), () -> startWaitingForItemSelection("[Jobs] Faites un clique droit avec l'object que vous voulez ajouter : ", "newXpSource"));

        addItem(45, createItemStack("Retour", Material.TIPPED_ARROW), () -> new ModificationTypeSelector(player, jobToModify).openInventory());

        HashMap<ItemStack, String> actions = loadXpSources();



        Object[] items = actions.keySet().toArray();
        for (int i = 0; i < actions.size(); i++) {
            ItemStack itemStack = (ItemStack) items[i];

            addItem(i + 9, itemStack, () -> {
                String actionToEdit = actions.get(itemStack);

                new XpSourceEditor(player, wayToXP, actionToEdit, jobToModify).openInventory();
            });
        }
    }

    protected abstract boolean isAValidXpSource(Object xpSource);

    protected abstract HashMap<ItemStack, String> loadXpSources();


    @Override
    public void computeSelectedItem(ItemStack item) {
        switch (itemSelectionDestination) {
            case "newXpSource":
                if (isAValidXpSource(item)) {
                    jobToModify.addXpSource(wayToXP, item.getType().toString(), null);
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
