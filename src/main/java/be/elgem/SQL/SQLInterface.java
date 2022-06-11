package be.elgem.SQL;

import be.elgem.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.*;
import java.util.UUID;

public class SQLInterface {
    final private String URL;
    final private String USER;
    final private String PASSWORD;

    private final ConfigurationSection CONFIGURATION;

    private Connection connection;


    public SQLInterface() {
        this.CONFIGURATION = Main.getMain().getConfig().getConfigurationSection("sql_database");

        URL = CONFIGURATION.getString("host") + ":" + CONFIGURATION.getString("port") + "/" + CONFIGURATION.getString("database_name") + "?useSSL=false";
        USER = CONFIGURATION.getString("user");
        PASSWORD = CONFIGURATION.getString("password");

    }

    private void connectToDatabase() {
        try {
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }

        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println("Connection to jobs database failed !");
        }
    }

    public void createTablesIfPossible() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
            connectToDatabase();

            try {
                Statement playerStatement = connection.createStatement();
                String playersTableRequest =    "CREATE TABLE IF NOT EXISTS players(" +
                                                "player_id MEDIUMINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                                                "player_uuid BINARY(16) NOT NULL UNIQUE);";

                playerStatement.execute(playersTableRequest);

                playerStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to create player table");
            }

            try {
                Statement jobsStatement = connection.createStatement();
                String jobsTableRequest =       "CREATE TABLE IF NOT EXISTS players_jobs(" +
                                                "job_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                                "player_id MEDIUMINT UNSIGNED NOT NULL," +
                                                "job_name VARCHAR(20) NOT NULL, job_level SMALLINT NOT NULL," +
                                                "job_experience INT NOT NULL," +
                                                "FOREIGN KEY(player_id) REFERENCES players(player_id)," +
                                                "INDEX ind_player_id (job_name))" +
                                                "ENGINE=INNODB;";

                jobsStatement.execute(jobsTableRequest);

                jobsStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to create job table");
            }

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadJobsData(String job, UUID playerUUID, SQLCallback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
            connectToDatabase();

            Statement jobStatement = null;

            try {
                int id = getPlayerID(playerUUID);

                if(id == -1){
                    callback.retrievePlayerInfo((short)1, 0);
                    return;
                }

                jobStatement = connection.createStatement();

                String jobQuery = "SELECT job_level AS level, job_experience AS xp FROM players_jobs WHERE player_id = " + id + " AND job_name = \"" + job + "\";";

                ResultSet resultSet = jobStatement.executeQuery(jobQuery);

                if(resultSet.next())
                    callback.retrievePlayerInfo(resultSet.getShort("level"), resultSet.getInt("xp"));
                else
                    callback.retrievePlayerInfo((short)1, 0);

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {if(jobStatement != null) jobStatement.close();}catch (Exception e){}
            }
        });
    }

    public void actualizeDatas(String players, String jobsData) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () ->{
            Statement insertStatement = null;
            try{
                insertStatement = connection.createStatement();

                String insertRequest = "INSERT IGNORE INTO players(player_uuid) VALUES " + players + ";";
                insertRequest += "INSERT INTO players_jobs() VALUES" + jobsData + ";";

                insertStatement.execute(insertRequest);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try{if(insertStatement!=null) insertStatement.close();}catch(Exception e){}
            }
        });

    }

//    private int getPlayerID(UUID playerUUID) {
//        Statement playerIDStatement = null;
//        try {
//            playerIDStatement = connection.createStatement();
//
//            String getId = "SELECT player_id AS id FROM players WHERE player_uuid = " + UUIDToBytes(playerUUID) + ";";
//
//            ResultSet resultSet = playerIDStatement.executeQuery(getId);
//
//            int id = -1;
//
//            if(resultSet.next()){
//                id = resultSet.getInt("id");
//            }
//
//            playerIDStatement.close();
//
//            return id;
//
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {if(playerIDStatement != null) playerIDStatement.close();} catch (Exception e){}
//        }
//
//        return -1;
//    }

    public String UUIDToBytes(UUID playerUUID) {
        byte[] binaryUUID = new byte[16];
        ByteBuffer.wrap(binaryUUID)
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(playerUUID.getLeastSignificantBits())
                .putLong(playerUUID.getMostSignificantBits());

        return binaryUUID.toString().replaceAll("\\[B@", "@B");
    }

//        public boolean isPlayerInDatabase(UUID playerUUID) {
//        Statement getPlayerStatement = null;
//        boolean exists = false;
//
//        try{
//            getPlayerStatement = connection.createStatement();
//
//            String isPresentRequest = "SELECT EXISTS(SELECT * FROM players WHERE player_uuid = " + UUIDToBytes(playerUUID) + ") AS exists;";
//
//            ResultSet resultSet = getPlayerStatement.executeQuery(isPresentRequest);
//
//
//
//            if(resultSet.next()) {
//                exists = resultSet.getBoolean("exists");
//            }
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try{if(getPlayerStatement!=null) getPlayerStatement.close();}catch(Exception e){}
//        }
//
//        return exists;
//    }
}
