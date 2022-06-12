package be.elgem.Jobs.Player;

import be.elgem.Jobs.Display.Display;
import be.elgem.Jobs.Misc.EWayToXP;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.Level;
import be.elgem.Main;
import org.bukkit.entity.Player;

public class PlayerJobData {
    private Job job;

    private Level level;

    private Player player;

    private Display infoDisplay;

    public PlayerJobData(Job job, Level level, Player player) {
        this.job = job;
        this.level = level;
        this.player = player;

        this.infoDisplay = Main.getMain().getJobsInfoDisplay();
    }

    public void tryAddXP(EWayToXP wayToXP, String xpSource) {
        int experienceToAdd = job.getXpFor(wayToXP, xpSource, level.getLevel());

        if(experienceToAdd == 0 ){
            return;
        }

        level.addExperience(experienceToAdd);
        infoDisplay.showNewXP(player, job.getJobName(), level.getLevel(), level.getExperience(), level.getMaxLevelExperience(), experienceToAdd);
    }

    public Level getLevel() {
        return level;
    }

    public Job getJob() {
        return job;
    }
}
