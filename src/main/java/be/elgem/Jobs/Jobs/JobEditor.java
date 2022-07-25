package be.elgem.Jobs.Jobs;

import be.elgem.Configuration.CustomConfigurationInterface;
import be.elgem.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class JobEditor {
    public static void changeIcon(String jobName, Material icon) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(jobName);

        jobConfig.set("display", icon.toString());

        customConfigurationInterface.saveConfiguration();
    }
}
