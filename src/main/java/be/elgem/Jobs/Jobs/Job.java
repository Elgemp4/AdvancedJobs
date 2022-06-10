package be.elgem.Jobs.Jobs;

import be.elgem.Jobs.Misc.AmountOfXp;
import be.elgem.Jobs.Misc.EWayToXP;

import java.util.HashMap;

/**
 * Represent a single job
 */
public class Job {
    private String jobName;

    private int maxLevel;
    private int experienceGrowth;
    private int firstLevelExperience;

    /**
     * BREAK :
     *      WOOD:
     *          5: 20
     *          10: 10
     *          15: 1-5
     */
    private HashMap<EWayToXP, HashMap<String, AmountOfXp>> xpSources;

//    private HashMap<Integer, ItemStack> reward; TODO

    public Job(String jobName, int maxLevel, int firstLevelExperience, int experienceGrowth) {
        this.jobName = jobName;

        this.maxLevel = maxLevel;
        this.experienceGrowth = experienceGrowth;
        this.firstLevelExperience = firstLevelExperience;

        this.xpSources = new HashMap<>();

        for(EWayToXP wayToXP : EWayToXP.values()){
            xpSources.put(wayToXP, new HashMap<>());
        }
    }

    public void addXpSource(EWayToXP wayToXP, String xpSource, AmountOfXp xpPerLevel){
        xpSources.get(wayToXP).put(xpSource, xpPerLevel);
    }

    public int getXpFor( EWayToXP wayToXP, String experienceSource, int playerLevel) {
        if(xpSources.get(wayToXP).containsKey(experienceSource)){
            return xpSources.get(wayToXP).get(experienceSource).getAmountOfXp(playerLevel);
        }
        else{
            return 0;
        }
    }

    public Job(String jobName) {
        this.jobName = jobName;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getExperienceGrowth() {
        return experienceGrowth;
    }

    public int getFirstLevelExperience() {
        return firstLevelExperience;
    }

    public String getJobName() {
        return jobName;
    }

}
