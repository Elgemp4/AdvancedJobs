package be.elgem.Jobs.Player;

import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.Level;
import be.elgem.Main;
import be.elgem.SQL.SQLInterface;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerJobsHandler {

    private ArrayList<PlayerJobData> playerJobData;

    public PlayerJobsHandler(Player player) {
        SQLInterface sqlInterface = Main.getMain().getSQLInterface();

        for(Job job : Main.getMain().getJobsLoader().getJobsArray()){

            sqlInterface.loadJobsData(job.getJobName(), player.getUniqueId(), (level, experience) -> {
                playerJobData.add(new PlayerJobData(job, new Level(level, job.getMaxLevel(), experience, job.getFirstLevelExperience(), job.getExperienceGrowth())));
            });
        }
    }

    public ArrayList<PlayerJobData> getPlayerJobData() {
        return playerJobData;
    }
}
