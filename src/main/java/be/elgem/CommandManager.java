package be.elgem;

import be.elgem.Gui.EditJobChooserGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) {return false;}
        if(!label.equals("jobs")){return false;}
        Player player = (Player)commandSender;

        switch (args[0]){
            case "edit":
                new EditJobChooserGUI(player).openInventory();
                break;
            default:
                player.sendMessage(ChatColor.RED + "Incorrect usage : /jobs <arg>");
                return false;
        }

        return true;
    }
}
