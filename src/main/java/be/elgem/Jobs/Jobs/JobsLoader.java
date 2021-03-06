package be.elgem.Jobs.Jobs;

import be.elgem.Jobs.Misc.AmountOfXp;
import be.elgem.Jobs.Misc.EWayToXP;
import be.elgem.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Loads the jobs from config
 */
public class JobsLoader {
    private ArrayList<Job> jobsArray;

    private Configuration pluginConfig;

    public JobsLoader() {
        this.pluginConfig = Main.getMain().getJobsConfig();

        loadJobs();

//        for (Job job: jobsArray) {
//            System.out.println(job.getJobName());
//            System.out.println(job.getXpFor(EWayToXP.BREAK, "minecraft:oak_log", 10));
//        }
    }

    private void loadJobs() {
        jobsArray = new ArrayList<>();

        for (String jobsName : pluginConfig.getKeys(false)) {
            ConfigurationSection jobSection = pluginConfig.getConfigurationSection(jobsName);

            short maxLevel = (short) jobSection.getInt("max_level");
            int firstLevelExperience = jobSection.getInt("first_level_experience");
            int experienceGrowth = jobSection.getInt("experience_growth");

            Job currentlyCreatingJob = new Job(jobsName, maxLevel, firstLevelExperience, experienceGrowth);

            loadWayToXp(currentlyCreatingJob, jobSection.getConfigurationSection("experience_sources"));

            jobsArray.add(currentlyCreatingJob);
        }
    }

    private void loadWayToXp(Job currentlyCreatingJob, ConfigurationSection wayToXpSection){
        for (String wayToXp : wayToXpSection.getKeys(false)) {
            ConfigurationSection experienceSources = wayToXpSection.getConfigurationSection(wayToXp);

            for (String experienceSource : experienceSources.getKeys(false)) {
                EWayToXP eWayToXP = EWayToXP.valueOf(wayToXp);

                AmountOfXp amountOfXp = loadAmountsOfXp(experienceSources.getConfigurationSection(experienceSource));

                currentlyCreatingJob.addXpSource(eWayToXP, experienceSource, amountOfXp);
            }
        }
    }

    private AmountOfXp loadAmountsOfXp(ConfigurationSection amountOfXpSection) {
        AmountOfXp amountOfXp = new AmountOfXp();

        if(amountOfXpSection.getKeys(false).isEmpty()){ //Case of single XP for the whole job
            String path = amountOfXpSection.getCurrentPath();
            amountOfXp.addXpForLevel(0, pluginConfig.getInt(path));
            return amountOfXp;
        }

        for (String requiredLevel : amountOfXpSection.getKeys(false)) {
            try{
                int requiredLevelInt = Integer.parseInt(requiredLevel);

                //Variable Experience
                if(amountOfXpSection.getString(requiredLevel).contains("/")) {
                    String[] xp = amountOfXpSection.getString(requiredLevel).replaceAll("\\s", "").split("/");
                    amountOfXp.addXpForLevel(requiredLevelInt, Integer.parseInt(xp[0]), Integer.parseInt(xp[1]));
                }

                //Set Experience
                else{
                    amountOfXp.addXpForLevel(requiredLevelInt, amountOfXpSection.getInt(requiredLevel));
                }
            }
            catch (NumberFormatException e) {
                Bukkit.getLogger().log(Level.SEVERE, "Error, syntax error in job config");
            }
        }

        return amountOfXp;
    }

    public ArrayList<Job> getJobsArray() {
        return jobsArray;
    }

    public Job getJobByName(String jobName) {
        for (Job job : jobsArray) {
            if (job.getJobName().equals(jobName)){
                return job;
            }
        }

        return null;
    }
}
