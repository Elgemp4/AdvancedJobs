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

    public ServerWideJobHandler() {
        this.sqlInterface = Main.getMain().getSQLInterface();

        serverWideJobsMap = new HashMap<>();

        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () ->{
            sendAllDataToDatabase();
        }, 20, 120);
    }

    private void sendAllDataToDatabase(){
        for (UUID playerUUID : serverWideJobsMap.keySet()) {
            for (PlayerJobData playerJobData : serverWideJobsMap.get(playerUUID).getPlayerJobData()) {
                Level playerLevel = playerJobData.getLevel();
                sqlInterface.updatePlayerLevel(playerLevel.getLevel(), playerLevel.getExperience(), playerUUID, playerJobData.getJob().getJobName());
            }
        }
    }

    public void addJobHandler(UUID playerUUID) {
        serverWideJobsMap.put(playerUUID, new PlayerJobsHandler(playerUUID));
    }

    public PlayerJobsHandler getPlayerJobsHandle(UUID playerUUID) {
        return serverWideJobsMap.get(playerUUID);
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
