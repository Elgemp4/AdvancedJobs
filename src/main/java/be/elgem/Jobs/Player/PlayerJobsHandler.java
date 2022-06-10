package be.elgem.Jobs.Player;

import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.Level;
import be.elgem.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerJobsHandler {
    private ArrayList<PlayerJobData> playerJobData;

    private Player player;

    public PlayerJobsHandler() {
        for(Job job : Main.getMain().getJobsLoader().getJobsArray()){
            playerJobData.add(new PlayerJobData(job, new Level(1, job.getMaxLevel(), 0, job.getFirstLevelExperience(), job.getExperienceGrowth())));
        }
    }
}
