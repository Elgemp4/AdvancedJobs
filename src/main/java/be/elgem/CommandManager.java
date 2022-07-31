package be.elgem;

import be.elgem.Gui.Admin.ChooseJobToEditGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) {return false;}
        if(!label.equals("jobs")){return false;}
        Player player = (Player)commandSender;

        switch (args[0]){
            case "edit":
                new ChooseJobToEditGUI(player).openInventory();
                break;
            default:
                player.sendMessage(ChatColor.RED + "Incorrect usage : /jobs <arg>");
                return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        if(!label.equals("jobs")){return null;}

        ArrayList<String> completions = new ArrayList<>();
        completions.add("edit");
        return completions;
    }
}
