package be.elgem.Configuration;

import be.elgem.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CustomConfigurationInterface {
    Main main;

    private File customFile;

    private FileConfiguration customConfigFile;

    public CustomConfigurationInterface(String fileName) {
        main = Main.getMain();

        customFile = new File(main.getDataFolder(), fileName);

        main.saveResource(fileName, false);

        customConfigFile = YamlConfiguration.loadConfiguration(customFile);
    }

    public FileConfiguration getCustomConfigFile() {
        return customConfigFile;
    }
}
