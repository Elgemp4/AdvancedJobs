package be.elgem.SQL;

import be.elgem.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

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

    public void closeConnection() {
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTablesIfPossible() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
            connectToDatabase();

//            try {
//                Statement playerStatement = connection.createStatement();
//                String playersTableRequest =    "CREATE TABLE IF NOT EXISTS players(" +
//                                                "player_id MEDIUMINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
//                                                "player_uuid BINARY(16) NOT NULL UNIQUE);";
//
//                playerStatement.execute(playersTableRequest);
//
//                playerStatement.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.out.println("Failed to create player table");
//            }

            try {
                Statement jobsStatement = connection.createStatement();
                String jobsTableRequest =       "CREATE TABLE IF NOT EXISTS players_jobs(" +
                                                "job_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                                "player_uuid Binary(16) NOT NULL," +
                                                "job_uuid Binary(16) NOT NULL, " +
                                                "job_level SMALLINT NOT NULL," +
                                                "job_experience INT NOT NULL," +
                                                "UNIQUE KEY uidx_job (job_uuid, player_uuid))" +
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

    public void loadJobsData(UUID jobUUID, UUID playerUUID, SQLCallback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
            connectToDatabase();

            PreparedStatement jobStatement = null;

            try {
                jobStatement = connection.prepareStatement( "SELECT job_level AS level, job_experience AS xp " +
                                                                "FROM players_jobs " +
                                                                "WHERE player_uuid = UNHEX(?) AND job_uuid = UNHEX(?);");

                jobStatement.setString(1, UUIDToBytes(playerUUID));
                jobStatement.setString(2, UUIDToBytes(jobUUID));

                ResultSet resultSet = jobStatement.executeQuery();

                if(resultSet.next())
                    callback.retrievePlayerInfo(resultSet.getShort("level"), resultSet.getInt("xp"));
                else
                    callback.retrievePlayerInfo((short)1, 0);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to load job data");
            } finally {
                try {if(jobStatement != null) jobStatement.close();}catch (Exception e){}
            }
        });
    }

    public void updatePlayerLevel(Short level, int experience, UUID playerUUID, UUID jobUUID) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () ->{
            connectToDatabase();

            PreparedStatement updateStatement = null;
            try{
                updateStatement = connection.prepareStatement(  "UPDATE players_jobs " +
                                                                    "SET job_level = ?, job_experience = ? " +
                                                                    "WHERE player_uuid = UNHEX(?) AND job_uuid = UNHEX(?);");

                updateStatement.setShort(1, level);
                updateStatement.setInt(2, experience);
                updateStatement.setString(3, UUIDToBytes(playerUUID));
                updateStatement.setString(4, UUIDToBytes(jobUUID));

                updateStatement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to update player level");
            }
            finally {
                try {if(updateStatement!=null) updateStatement.close();} catch(Exception e){}
            }
        });
    }

    public void doesPlayerJobExists(UUID playerUUID, UUID jobUUID, SQLCallback sqlCallback){
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () ->{
            connectToDatabase();

            PreparedStatement existStatement = null;
            try{
                existStatement = connection.prepareStatement("SELECT EXISTS( SELECT * FROM players_jobs WHERE(player_uuid = UNHEX(?) AND job_uuid = UNHEX(?))) AS exist;");

                existStatement.setString(1, UUIDToBytes(playerUUID));
                existStatement.setString(2, UUIDToBytes(jobUUID));

                ResultSet resultSet = existStatement.executeQuery();

                if(resultSet.next()) {
                    if(!resultSet.getBoolean("exist")) {
                        sqlCallback.retrievePlayerInfo((short) -1, -1);
                    }

                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to find out if player job exists");
            }
            finally {
                try {if(existStatement!=null) existStatement.close();} catch(Exception e){}
            }
        });
    }

    public void insertPlayerJob(UUID playerUUID, UUID jobUUID) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () ->{
            connectToDatabase();

            PreparedStatement insertStatement = null;
            try{
                insertStatement = connection.prepareStatement(  "INSERT INTO players_jobs (player_uuid, job_uuid, job_level, job_experience) " +
                                                                    "VALUES(UNHEX(?), UNHEX(?), 1, 0);");

                insertStatement.setString(1, UUIDToBytes(playerUUID));
                insertStatement.setString(2, UUIDToBytes(jobUUID));

                insertStatement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to insert player job");
            }
            finally {
                try {if(insertStatement!=null) insertStatement.close();} catch(Exception e){}
            }
        });

    }


    public String UUIDToBytes(UUID playerUUID) {
        String stringUUID = playerUUID.toString().replaceAll("-","");

        return stringUUID;
    }
}
