package be.elgem.Jobs.Display;

import org.bukkit.entity.Player;

public abstract class Display {

    protected String formatXPText(String jobName, int level, int experience, int maxExperience, int addedExperience){
        String addedXpSign = ((addedExperience>0) ? "+" : "");

        return String.format("§b%s [Level %o] : §e%o xp / %o xp §a(%s%o xp) ", jobName, level, experience, maxExperience, addedXpSign, addedExperience);
    }

    public abstract void showNewXP(Player player, String jobName, int level, int experience, int maxExperience, int addedExperience);

    public abstract void showLevelUp(int newLevel);

    public abstract void showLevelDown(int newLevel);
}
