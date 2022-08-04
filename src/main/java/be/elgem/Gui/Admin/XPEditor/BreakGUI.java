package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.EXpMethod;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BreakGUI extends XPEditorInsertGUI{
    public BreakGUI(Player player, Job editedJob, GUI previousGUI) {
        super(player, EXpMethod.BREAK, editedJob, previousGUI);
    }

    @Override
    protected String getXpSourceFromItemStack(ItemStack itemStack) {
        if(itemStack.getType().isBlock()) {
            return itemStack.getType().toString();
        }else{
            return null;
        }
    }

    @Override
    protected HashMap<ItemStack, String> loadXpSources() {
        HashMap<ItemStack, String> xpSources =  new HashMap<>();

        for (String xpSource : jobToModify.getXpSources(EXpMethod.BREAK)) {
            xpSources.put(createItemStack(Material.valueOf(xpSource)), xpSource);
        }

        return xpSources;
    }
}
