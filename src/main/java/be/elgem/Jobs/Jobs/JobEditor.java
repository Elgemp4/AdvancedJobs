package be.elgem.Jobs.Jobs;

import be.elgem.Configuration.CustomConfigurationInterface;
import be.elgem.Jobs.Misc.AmountOfXp;
import be.elgem.Jobs.Misc.EWayToXP;
import be.elgem.Jobs.Misc.ExperienceValues;
import be.elgem.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.TreeMap;
import java.util.UUID;

public class JobEditor {

    public static void createJob(UUID uuid, String jobName, Material icon, int maxLevel, int firstLevelExperience, int experienceGrowth) {
        CustomConfigurationInterface config = Main.getMain().getJobsConfig();
        config.getCustomConfigFile().createSection(uuid.toString());

        changeIcon(uuid, icon);
        changeName(uuid, jobName);
        changeMaxLevel(uuid, maxLevel);
        changeFirstLevelExperience(uuid, firstLevelExperience);
        changeExperienceGrowth(uuid, experienceGrowth);

        config.getCustomConfigFile().createSection(uuid + ".experience_sources");

        config.saveConfiguration();
    }

    public static void changeIcon(UUID uuid, Material icon) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(uuid.toString());

        jobConfig.set("icon", icon.toString());

        customConfigurationInterface.saveConfiguration();
    }

    public static void changeName(UUID uuid, String newName) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(uuid.toString());

        jobConfig.set("name", newName);

        customConfigurationInterface.saveConfiguration();
    }

    public static void changeMaxLevel(UUID uuid, int maxLevel) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(uuid.toString());

        jobConfig.set("max_level", maxLevel);

        customConfigurationInterface.saveConfiguration();
    }

    public static void changeFirstLevelExperience(UUID uuid, int firstLevelExperience) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(uuid.toString());

        jobConfig.set("first_level_experience", firstLevelExperience);

        customConfigurationInterface.saveConfiguration();
    }

    public static void changeExperienceGrowth(UUID uuid, int experienceGrowth) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(uuid.toString());

        jobConfig.set("experience_growth", experienceGrowth);

        customConfigurationInterface.saveConfiguration();
    }


    public static void deleteJob(UUID jobUUID) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        customConfigurationInterface.getCustomConfigFile().set(jobUUID.toString(), null);
        customConfigurationInterface.saveConfiguration();
    }

    public static void addXpSource(UUID jobUUID, EWayToXP wayToXp, String experienceSource, AmountOfXp amountOfXp) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(jobUUID.toString() + ".experience_sources");

        jobConfig.createSection(wayToXp.toString() + "." + experienceSource);

        if(amountOfXp != null) {
            TreeMap<Integer, ExperienceValues> xpValues = amountOfXp.getAmountOfXpPerLevel();

            for (int level : xpValues.keySet()) {
                ExperienceValues values = xpValues.get(level);
                if(values.isSingleValue()){
                    jobConfig.set(wayToXp + "." + experienceSource + "." + level, values.getMin());
                }
                else{
                    jobConfig.set(wayToXp + "." + experienceSource + "." + level, values.getMin() + " / " + values.getMax());
                }
            }
        }


        customConfigurationInterface.saveConfiguration();
    }
}
