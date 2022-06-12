package be.elgem.Jobs.Player;

import be.elgem.Jobs.Jobs.Job;
import be.elgem.Jobs.Misc.EWayToXP;
import be.elgem.Jobs.Misc.Level;
import be.elgem.Main;
import be.elgem.SQL.SQLCallback;
import be.elgem.SQL.SQLInterface;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerJobsHandler {

    private ArrayList<PlayerJobData> playerJobData;

    public PlayerJobsHandler(Player player) {
        playerJobData = new ArrayList<>();

        UUID playerUUID = player.getUniqueId();

        SQLInterface sqlInterface = Main.getMain().getSQLInterface();

        for(Job job : Main.getMain().getJobsLoader().getJobsArray()){
            sqlInterface.doesPlayerJobExists(playerUUID, job.getJobName(), (level, experience) -> {
                if(level == -1) {
                    sqlInterface.insertPlayerJob(playerUUID, job.getJobName());
                }
            });
            sqlInterface.loadJobsData(job.getJobName(), playerUUID, (level, experience) -> {
                playerJobData.add(new PlayerJobData(job, new Level(level, job.getMaxLevel(), experience, job.getFirstLevelExperience(), job.getExperienceGrowth(), player), player));
            });
        }
    }

    public ArrayList<PlayerJobData> getPlayerJobData() {
        return playerJobData;
    }

    public void addXpToJob(EWayToXP eWayToXP, String xpSource){
        playerJobData.forEach((job) ->{
            job.tryAddXP(eWayToXP, xpSource);
        });
    }
}
