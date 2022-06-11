package be.elgem;

import be.elgem.Configuration.CustomConfigurationInterface;
import be.elgem.Jobs.Jobs.JobsLoader;
import be.elgem.SQL.SQLInterface;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main main;

    private JobsLoader jobsLoader;

    private CustomConfigurationInterface jobsConfig;

    private SQLInterface playerSQLInterface;

    @Override
    public void onEnable() {
        super.onEnable();

        Main.main = this;

        saveDefaultConfig();

        jobsConfig = new CustomConfigurationInterface("jobs.yml");

        jobsLoader = new JobsLoader();

        playerSQLInterface = new SQLInterface();

        playerSQLInterface.createTablesIfPossible();
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
}
