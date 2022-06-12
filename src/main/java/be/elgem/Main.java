package be.elgem;

import be.elgem.Configuration.CustomConfigurationInterface;
import be.elgem.Jobs.Jobs.JobsLoader;
import be.elgem.Jobs.Player.PlayerJobData;
import be.elgem.Jobs.Player.PlayerJobsHandler;
import be.elgem.Jobs.Player.ServerWideJobHandler;
import be.elgem.Listeners.ListenerManager;
import be.elgem.SQL.SQLInterface;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Main extends JavaPlugin {
    private static Main main;

    private JobsLoader jobsLoader;

    private CustomConfigurationInterface jobsConfig;

    private SQLInterface playerSQLInterface;

    private ServerWideJobHandler serverWideJobHandler;

    private ListenerManager listenerManager;

    @Override
    public void onEnable() {
        super.onEnable();

        Main.main = this;

        saveDefaultConfig();

        jobsConfig = new CustomConfigurationInterface("jobs.yml");

        listenerManager = new ListenerManager();

        jobsLoader = new JobsLoader();

        playerSQLInterface = new SQLInterface();

        playerSQLInterface.createTablesIfPossible();

        serverWideJobHandler = new ServerWideJobHandler();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Main getMain() {
        return Main.main;
    }

    public JobsLoader getJobsLoader() {
        return jobsLoader;
    }

    public FileConfiguration getJobsConfig() {
        return jobsConfig.getCustomConfigFile();
    }

    public SQLInterface getSQLInterface() {
        return playerSQLInterface;
    }

    public ServerWideJobHandler getServerWideJobHandler() {
        return serverWideJobHandler;
    }



}
