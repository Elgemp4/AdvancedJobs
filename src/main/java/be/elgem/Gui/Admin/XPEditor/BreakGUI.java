package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.EWayToXP;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BreakGUI extends XPEditorInsertGUI{
    public BreakGUI(Player player, EWayToXP wayToXP, String action, Job editedJob) {
        super(player, wayToXP, editedJob);
    }

    @Override
    protected void isAValidAction() {

    }

    @Override
    protected HashMap<ItemStack, String> loadAction() {
        return null;
    }

    @Override
    public void computeSelectedItem(ItemStack item) {

    }

    @Override
    public void computeInput(String input) {

    }
}
