package be.elgem.Jobs.Jobs;

import be.elgem.Jobs.Misc.AmountOfXp;
import be.elgem.Jobs.Misc.EWayToXP;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.UUID;

/**
 * Represent a single job
 */
public class Job {
    private String jobName;

    private UUID jobUUID;

    private Material icon;

    private short maxLevel;
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

//    private HashMap<Integer, ItemStack> reward; TODO système de récompense

    public Job(UUID uuid, String jobName, Material icon, short maxLevel, int firstLevelExperience, int experienceGrowth) {
        this.jobUUID = uuid;

        this.jobName = jobName;

        this.icon = icon;

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

    public short getMaxLevel() {
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

    public UUID getJobUUID() {
        return jobUUID;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material newIcon) {
        JobEditor.changeIcon(jobUUID, newIcon);

        this.icon = newIcon;
    }

    public void setJobName(String newName) {
        JobEditor.changeName(jobUUID, newName);

        this.jobName = newName;
    }
}
