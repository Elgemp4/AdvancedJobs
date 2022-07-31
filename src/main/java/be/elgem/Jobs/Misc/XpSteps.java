package be.elgem.Jobs.Misc;

import java.util.TreeMap;

public class XpSteps {
    private TreeMap<Integer, ExperienceValues> amountOfXpPerLevel;

    public XpSteps(){
        amountOfXpPerLevel = new TreeMap<>();
    }

    public void addXpForLevel(int requiredLevel, int level) {
        addXpForLevel(requiredLevel, level, level);
    }

    public void addXpForLevel(int requiredLevel, int minXp, int maxXp) {
        if(amountOfXpPerLevel.containsKey(requiredLevel)) {
            amountOfXpPerLevel.remove(requiredLevel);
        }
        amountOfXpPerLevel.put(requiredLevel, new ExperienceValues(maxXp, minXp));
    }

    public void removeXpForLevel(int level) {
        if(amountOfXpPerLevel.containsKey(level)) {
            amountOfXpPerLevel.remove(level);
        }
    }

    public int getAmountOfXp(int level) {
        for (int levelStep : amountOfXpPerLevel.descendingKeySet()) {
            int minX = amountOfXpPerLevel.get(levelStep).getMin();
            int maxX = amountOfXpPerLevel.get(levelStep).getMax();

            if (levelStep <= level) {
                if(minX == maxX){
                    return minX;
                }
                else{
                    return (int) (Math.random() * (maxX - minX + 1) + minX);
                }
            }
        }

        return 0;
    }

    public TreeMap<Integer, ExperienceValues> getAmountOfXpPerLevel() {
        return amountOfXpPerLevel;
    }

    public void modifyLevel(int oldLevel, int newLevel) {
        amountOfXpPerLevel.put(newLevel, amountOfXpPerLevel.get(oldLevel));
        amountOfXpPerLevel.remove(oldLevel);
    }


}
