package be.elgem;

import be.elgem.Configuration.CustomConfigurationInterface;
import be.elgem.Gui.OpenedGUI;
import be.elgem.Jobs.Display.Display;
import be.elgem.Jobs.Display.WitherBossBarDisplay;
import be.elgem.Jobs.Jobs.JobsLoader;
import be.elgem.Jobs.Player.PlayerJobData;
import be.elgem.Jobs.Player.PlayerJobsHandler;
import be.elgem.Jobs.Player.ServerWideJobHandler;
import be.elgem.Listeners.ChunkLoadingListener;
import be.elgem.Listeners.ListenerManager;
import be.elgem.SQL.SQLInterface;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import sun.awt.windows.WPrinterJob;

public class Main extends JavaPlugin {
    private static Main main;

    private JobsLoader jobsLoader;

    private CustomConfigurationInterface jobsConfig;

    private SQLInterface playerSQLInterface;

    private ServerWideJobHandler serverWideJobHandler;

    private ListenerManager listenerManager;

    private CommandManager commandManager;

    private OpenedGUI openedGUI;

    private Display jobsInfoDisplay;

    @Override
    public void onEnable() {
        super.onEnable();

        Main.main = this;

        saveDefaultConfig();

        jobsConfig = new CustomConfigurationInterface("jobs.yml");

        jobsLoader = new JobsLoader();

        playerSQLInterface = new SQLInterface();

        playerSQLInterface.createTablesIfPossible();

        loadDisplay();

        serverWideJobHandler = new ServerWideJobHandler();

        openedGUI = new OpenedGUI();

        listenerManager = new ListenerManager();

        commandManager = new CommandManager();

        this.getCommand("jobs").setExecutor(commandManager);

        loadConnectedPlayer();

        loadChunks();
    }

    @Override
    public void onDisable() {
        super.onDisable();

        ChunkLoadingListener.getChunkLoadingListener().saveAllChunks();

        playerSQLInterface.closeConnection();
    }

    private void loadConnectedPlayer() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            serverWideJobHandler.addJobHandler(player);
        }
    }

    private void loadChunks() {
        for (World world : Bukkit.getWorlds()){
            ChunkLoadingListener.getChunkLoadingListener().loadChunks(world.getLoadedChunks());
        }

    }
    private void loadDisplay() {
        jobsInfoDisplay = new WitherBossBarDisplay();
    }



    public static Main getMain() {
        return Main.main;
    }

    public JobsLoader getJobsLoader() {
        return jobsLoader;
    }

    public CustomConfigurationInterface getJobsConfig() {
        return jobsConfig;
    }

    public SQLInterface getSQLInterface() {
        return playerSQLInterface;
    }

    public ServerWideJobHandler getServerWideJobHandler() {
        return serverWideJobHandler;
    }

    public Display getJobsInfoDisplay() {
        return jobsInfoDisplay;
    }

    public OpenedGUI getOpenedGUI() {
        return openedGUI;
    }
}
