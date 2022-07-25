package be.elgem.Gui;

import be.elgem.Jobs.Jobs.Job;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class XPModifierGUI extends GUI{
    public XPModifierGUI(Player player, Job jobToModify) {
        super(player, 27, ChatColor.RED + "Choose the job to edit");

        createInventory();
    }

    @Override
    protected void createInventory() {

    }
}
