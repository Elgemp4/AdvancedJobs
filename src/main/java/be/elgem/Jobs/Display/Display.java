package be.elgem.Jobs.Display;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public abstract class Display {

    protected String formatXPText(String jobName, int level, int experience, int maxExperience, int addedExperience){
        String addedXpSign = ((addedExperience>0) ? "+" : "");

        return "§b"+ jobName + " [Level " + level + "] : §e" + experience + " xp / " + maxExperience + " xp §a(" + addedXpSign + addedExperience + " xp) ";
    }

    public abstract void showNewXP(Player player, String jobName, int level, int experience, int maxExperience, int addedExperience);

    public void showLevelUp(int newLevel, Player player){
        player.sendTitle("§cYou have leveled up !", "§6Level " + newLevel, 5, 60, 5);
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 100000, 1);
    }

    public void showLevelDown(int newLevel){

    }
}
