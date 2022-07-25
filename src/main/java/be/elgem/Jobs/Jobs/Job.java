package be.elgem.Jobs.Jobs;

import be.elgem.Jobs.Misc.AmountOfXp;
import be.elgem.Jobs.Misc.EWayToXP;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

/**
 * Represent a single job
 */
public class Job {
    private String jobName;

    private ItemStack displayItem;

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

    public Job(String jobName, ItemStack displayItem, short maxLevel, int firstLevelExperience, int experienceGrowth) {
        this.jobName = jobName;

        this.displayItem = displayItem;

        ItemMeta meta = this.displayItem.getItemMeta();
        meta.setDisplayName(ChatColor.BOLD + jobName);
        this.displayItem.setItemMeta(meta);

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

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public void setDisplayItem(Material newIcon) {
        JobEditor.changeIcon(jobName, newIcon);

        this.displayItem = new ItemStack(newIcon);
        ItemMeta meta = this.displayItem.getItemMeta();
        meta.setDisplayName(ChatColor.BOLD + jobName);
        this.displayItem.setItemMeta(meta);
    }
}
