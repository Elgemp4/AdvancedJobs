package be.elgem.Gui.Admin.XPEditor;

import be.elgem.Gui.GUI;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.EXpMethod;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KillGUI extends XPEditorInsertGUI {
    public KillGUI(Player player, Job editedJob, GUI previousGUI) {
        super(player, EXpMethod.KILL, editedJob, previousGUI);
    }

    @Override
    protected String getXpSourceFromItemStack(ItemStack itemStack) {
        if(itemStack.getType().toString().contains("SPAWN_EGG")) {
            EntityType entityType = spawnEggToEntity(itemStack.getType().toString());
            return entityType.toString();
        }
        else{
            return null;
        }
    }

    @Override
    protected HashMap<ItemStack, String> loadXpSources() {
        HashMap<ItemStack, String> xpSources =  new HashMap<>();

        for(String entity : jobToModify.getXpSources(xpMethod)) {
            ItemStack item = createItemStack(entityToSpawnEgg(entity));
            xpSources.put(item, entity);
        }

        return  xpSources;
    }

    private Material entityToSpawnEgg(String entity) {
        return Material.getMaterial(entity + "_SPAWN_EGG");
    }

    private EntityType spawnEggToEntity(String spawnEgg) {
        return EntityType.valueOf(spawnEgg.replace("_SPAWN_EGG", ""));
    }
}
