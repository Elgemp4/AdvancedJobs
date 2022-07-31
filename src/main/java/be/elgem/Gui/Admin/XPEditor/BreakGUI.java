package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.EXpMethod;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BreakGUI extends XPEditorInsertGUI{
    public BreakGUI(Player player, Job editedJob) {
        super(player, EXpMethod.BREAK, editedJob);
    }

    @Override
    protected boolean isAValidXpSource(Object xpSource) {
        if(!(xpSource instanceof ItemStack)) {return false;}
        ItemStack item = (ItemStack) xpSource;
        return item.getType().isBlock();
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
