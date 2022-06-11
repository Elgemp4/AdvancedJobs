package be.elgem.Jobs.Player;

import be.elgem.Jobs.Misc.EWayToXP;
import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.Level;

public class PlayerJobData {
    private Job job;

    private Level level;

    public PlayerJobData(Job job, Level level) {
        this.job = job;
        this.level = level;
    }

    public void tryAddXP(EWayToXP wayToXP, String xpSource) {
        int experienceToAdd = job.getXpFor(wayToXP, xpSource, level.getLevel());

        if(experienceToAdd == 0 ){
            return;
        }

        level.addExperience(experienceToAdd);
    }

    public Level getLevel() {
        return level;
    }

    public Job getJob() {
        return job;
    }
}
