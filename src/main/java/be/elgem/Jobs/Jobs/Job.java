package be.elgem.Jobs.Jobs;

import be.elgem.Jobs.Misc.XpSteps;
import be.elgem.Jobs.Misc.EXpMethod;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.UUID;

/**
 * Represent a single job
 */
public class Job {
    private String jobName;

    final private UUID jobUUID;

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
    final private HashMap<EXpMethod, HashMap<String, XpSteps>> xpSources;

//    private HashMap<Integer, ItemStack> reward; TODO système de récompense

    public Job(UUID uuid, String jobName, Material icon, short maxLevel, int firstLevelExperience, int experienceGrowth) {
        this.jobUUID = uuid;

        this.jobName = jobName;

        this.icon = icon;

        this.maxLevel = maxLevel;
        this.experienceGrowth = experienceGrowth;
        this.firstLevelExperience = firstLevelExperience;

        this.xpSources = new HashMap<>();

        for(EXpMethod wayToXP : EXpMethod.values()){
            xpSources.put(wayToXP, new HashMap<>());
        }
    }

    public void addXpSource(EXpMethod wayToXP, String xpSource, XpSteps xpPerLevel){
        xpSources.get(wayToXP).put(xpSource, xpPerLevel);

        if(xpPerLevel == null) {
            JobEditor.addXpSource(jobUUID, wayToXP, xpSource);
        }
    }

    public int getXpFor(EXpMethod wayToXP, String experienceSource, int playerLevel) {
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

    public void setFirstLevelExperience(int newFirstLevelExperience) {
        JobEditor.changeFirstLevelExperience(jobUUID, newFirstLevelExperience);
        
        this.firstLevelExperience = newFirstLevelExperience;
    }

    public void setExperienceGrowth(int newExperienceGrowth) {
        JobEditor.changeExperienceGrowth(jobUUID, newExperienceGrowth);

        this.experienceGrowth = newExperienceGrowth;
    }

    public void setMaxLevel(int newMaxLevel) {
        JobEditor.changeMaxLevel(jobUUID, newMaxLevel);

        this.maxLevel = (short) newMaxLevel;
    }

    public String[] getXpSources(EXpMethod wayToXP) {
        return xpSources.get(wayToXP).keySet().toArray(new String[0]);
    }

    public XpSteps getXpSteps(EXpMethod wayToXP, String xpSource) {
        return xpSources.get(wayToXP).get(xpSource);
    }

    public void addXpStep(EXpMethod xpMethod, String xpSourceToEdit, int level, int xp) {
        JobEditor.addXpStep(jobUUID, xpMethod, xpSourceToEdit, level, xp);

        if(!xpSources.get(xpMethod).containsKey(xpSourceToEdit)){
            xpSources.put(xpMethod, new HashMap<>());
        }

        if(xpSources.get(xpMethod).get(xpSourceToEdit) == null){
            xpSources.get(xpMethod).put(xpSourceToEdit, new XpSteps());
        }
        System.out.println(xpSources);
        System.out.println(xpSources.get(xpMethod));
        System.out.println(xpSources.get(xpMethod).get(xpSourceToEdit));

        xpSources.get(xpMethod).get(xpSourceToEdit).addXpForLevel(level, xp);
    }

    public void addXpStep(EXpMethod xpMethod, String xpSourceToEdit, int level, int min, int max) {
        JobEditor.addXpStep(jobUUID, xpMethod, xpSourceToEdit, level, min, max);

        if(!xpSources.get(xpMethod).containsKey(xpSourceToEdit)){
            xpSources.put(xpMethod, new HashMap<>());
        }

        if(xpSources.get(xpMethod).get(xpSourceToEdit) == null){
            xpSources.get(xpMethod).put(xpSourceToEdit, new XpSteps());
        }

        xpSources.get(xpMethod).get(xpSourceToEdit).addXpForLevel(level, min, max);
    }

    public void removeXpStep(EXpMethod wayToXP, String xpSourceToEdit, int parseInt) {
        JobEditor.removeXpStep(jobUUID, wayToXP, xpSourceToEdit, parseInt);

        xpSources.get(wayToXP).get(xpSourceToEdit).removeXpForLevel(parseInt);
    }

    public void modifyLevelOfXpStep(EXpMethod xpMethod, String xpSourceToEdit, int oldLevel, int newLevel) {
        JobEditor.changeLevelOfStep(jobUUID, xpMethod, xpSourceToEdit, oldLevel, newLevel);
        this.xpSources.get(xpMethod).get(xpSourceToEdit).modifyLevel(oldLevel, newLevel);
    }

    public void removeXpSource(EXpMethod xpMethod, String xpSourceToEdit) {
        JobEditor.removeXpSource(jobUUID, xpMethod, xpSourceToEdit);

        xpSources.get(xpMethod).remove(xpSourceToEdit);
    }
}
