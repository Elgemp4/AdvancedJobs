package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.EWayToXP;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class XpSourceEditor extends GUI {
    protected Player player;

    protected EWayToXP wayToXP;

    protected Job jobToModify;

    public XpSourceEditor(Player player, EWayToXP wayToXP, String action, Job editedJob) {
        super(player, 27, "Editeur d'xp pour " + wayToXP.toString());

        this.player = player;

        this.jobToModify = editedJob;
    }

    @Override
    protected void createInventory() {
        for (int i = 0; i < 9; i++) {
            addItem(i, createItemStack(" ", Material.GREEN_STAINED_GLASS_PANE), null);
        }
    }

    @Override
    public void computeSelectedItem(ItemStack item) {

    }

    @Override
    public void computeInput(String input) {

    }
}
