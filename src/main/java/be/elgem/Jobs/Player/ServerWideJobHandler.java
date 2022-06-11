package be.elgem.Jobs.Player;

import be.elgem.Main;
import be.elgem.SQL.SQLInterface;

import java.util.HashMap;
import java.util.UUID;

public class ServerWideJobHandler {
    private SQLInterface sqlInterface;

    private HashMap<UUID, PlayerJobsHandler> serverWideJobsMap;

    private boolean firstPlayer = false;
    private boolean firstJob = false;

    public ServerWideJobHandler() {
        this.sqlInterface = Main.getMain().getSQLInterface();

        serverWideJobsMap = new HashMap<>();
    }

    private void sendAllDataToDatabase(){
        String playersToAdd = "";
        String jobsToModify = "";

        firstPlayer = true;
        firstJob = true;

        for (UUID playerUUID : serverWideJobsMap.keySet()) {
            playersToAdd+=formatNewPlayer(playerUUID);

            for (PlayerJobData playerJobData : serverWideJobsMap.get(playerUUID).getPlayerJobData()) {

            }


        }

        System.out.println(playersToAdd);
        System.out.println(jobsToModify);


        sqlInterface.actualizeDatas(playersToAdd, jobsToModify);
    }

    private String formatNewPlayer(UUID playerUUID) {
        String newPlayer = "";

        if(firstPlayer){
            firstPlayer = false;
        }
        else{
            newPlayer += ",";
        }
        newPlayer += "("+sqlInterface.UUIDToBytes(playerUUID)+")";

        return newPlayer;
    }

    private String formatJob() {
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
