package be.elgem.Jobs.Jobs;

import be.elgem.Configuration.CustomConfigurationInterface;
import be.elgem.Jobs.Misc.EXpMethod;
import be.elgem.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;


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

    public static void changeLevelOfStep(UUID jobUUID, EXpMethod xpMethod, String experienceSource, int previousLevel, int newLevel) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection xpSourceSection = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(jobUUID.toString() + ".experience_sources." + xpMethod.toString() + "." + experienceSource);
        xpSourceSection.set(""+newLevel, xpSourceSection.get(""+previousLevel));
        xpSourceSection.set(""+previousLevel, null);
        customConfigurationInterface.saveConfiguration();
    }

    public static void addXpSource(UUID jobUUID, EXpMethod xpMethod, String experienceSource) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(jobUUID.toString() + ".experience_sources");

        jobConfig.createSection(xpMethod.toString() + "." + experienceSource);

        customConfigurationInterface.saveConfiguration();
    }

    public static void addXpStep(UUID jobUUID, EXpMethod xpMethod, String xpSourceToEdit, int level, int min, int max) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(jobUUID.toString() + ".experience_sources");
        ConfigurationSection xpSource = jobConfig.getConfigurationSection(xpMethod.toString() + "." + xpSourceToEdit);

        xpSource.set(""+level, min + " / " + max);

        customConfigurationInterface.saveConfiguration();
    }

    public static void addXpStep(UUID jobUUID, EXpMethod xpMethod, String xpSourceToEdit, int level, int xp) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(jobUUID.toString() + ".experience_sources");
        ConfigurationSection xpSource = jobConfig.getConfigurationSection(xpMethod.toString() + "." + xpSourceToEdit);

        xpSource.set(""+level, xp);

        customConfigurationInterface.saveConfiguration();
    }

    public static void removeXpStep(UUID jobUUID, EXpMethod xpMethod, String xpSourceToEdit, int level) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(jobUUID.toString() + ".experience_sources");
        ConfigurationSection xpSource = jobConfig.getConfigurationSection(xpMethod.toString() + "." + xpSourceToEdit);

        xpSource.set(""+level, null);

        customConfigurationInterface.saveConfiguration();
    }

    public static void removeXpSource(UUID jobUUID, EXpMethod xpMethod, String xpSourceToEdit) {
        CustomConfigurationInterface customConfigurationInterface = Main.getMain().getJobsConfig();
        ConfigurationSection jobConfig = customConfigurationInterface.getCustomConfigFile().getConfigurationSection(jobUUID.toString() + ".experience_sources." + xpMethod.toString());
        jobConfig.set(xpSourceToEdit, null);
        System.out.println("ici");
        customConfigurationInterface.saveConfiguration();
    }
}
