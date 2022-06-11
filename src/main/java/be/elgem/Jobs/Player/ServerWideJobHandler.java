package be.elgem.Jobs.Player;

import be.elgem.Jobs.Misc.Level;
import be.elgem.Main;
import be.elgem.SQL.SQLInterface;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class ServerWideJobHandler {
    private SQLInterface sqlInterface;

    private HashMap<UUID, PlayerJobsHandler> serverWideJobsMap;

    private boolean firstJob = false;

    public ServerWideJobHandler() {
        this.sqlInterface = Main.getMain().getSQLInterface();

        serverWideJobsMap = new HashMap<>();

        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () ->{
            sendAllDataToDatabase();
        }, 20, 120);
    }

    private void sendAllDataToDatabase(){
        String jobsToModify = "";

        firstJob = true;

        for (UUID playerUUID : serverWideJobsMap.keySet()) {
            for (PlayerJobData playerJobData : serverWideJobsMap.get(playerUUID).getPlayerJobData()) {
                Level playerLevel = playerJobData.getLevel();
                jobsToModify += formatJob(playerUUID, playerJobData.getJob().getJobName(), playerLevel.getLevel(), playerLevel.getExperience());
            }


        }

        if(!jobsToModify.isEmpty()){
            sqlInterface.actualizeDatas(jobsToModify);
        }
    }


    private String formatJob(UUID playerUUID, String jobName, short level, int experience) {
        String newJob = "";

        if(firstJob){
            firstJob = false;
        }
        else{
            newJob += ",";
        }
        newJob += "("+sqlInterface.UUIDToBytes(playerUUID)+")";

        return newJob;
    }
}









//        for (UUID playerUUID : serverWideJobsMap.keySet()) {
//            if(!sqlInterface.isPlayerInDatabase(playerUUID)){
//                if(!firstPlayer)
//                    playersToAdd += ",";
//                else
//                    firstPlayer = false;
//
//                playersToAdd += "("+sqlInterface.UUIDToBytes(playerUUID)+")";
//            }
//        }
