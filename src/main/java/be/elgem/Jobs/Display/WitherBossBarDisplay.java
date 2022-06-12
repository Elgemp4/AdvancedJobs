package be.elgem.Jobs.Display;

import be.elgem.Main;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class WitherBossBarDisplay extends Display{
    private HashMap<Player, BossBar> playerBossBars;

    public WitherBossBarDisplay() {
        playerBossBars = new HashMap<>();
    }

    @Override
    public void showNewXP(Player player, String jobName, int level, int experience, int maxExperience, int addedExperience) {
        String displayText = formatXPText(jobName, level, experience, maxExperience, addedExperience);

        removeBossBarFromPlayer(player);

        BossBar bossBar = Bukkit.createBossBar(displayText, BarColor.BLUE, BarStyle.SEGMENTED_20);
        bossBar.setProgress((double)experience / (double)maxExperience);
        bossBar.addPlayer(player);

        playerBossBars.put(player, bossBar);

        removeAfterTwoSeconds(player, bossBar);
    }

    private void removeBossBarFromPlayer(Player player) {
        if(playerBossBars.containsKey(player)) {
            playerBossBars.get(player).removePlayer(player);
        }
    }

    private void removeAfterTwoSeconds(Player player, BossBar bossBar) {
        Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> {
            bossBar.removeAll();
        }, 40);
    }

    @Override
    public void showLevelUp(int newLevel) {

    }

    @Override
    public void showLevelDown(int newLevel) {

    }
}
