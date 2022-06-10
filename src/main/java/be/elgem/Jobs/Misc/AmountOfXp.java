package be.elgem.Jobs.Misc;

import java.util.TreeMap;

public class AmountOfXp {
    private TreeMap<Integer, ExperienceValues> amountOfXpPerLevel;

    public AmountOfXp(){
        amountOfXpPerLevel = new TreeMap<>();
    }

    public void addXpForLevel(int requiredLevel, int level) {
        addXpForLevel(requiredLevel, level, level);
    }

    public void addXpForLevel(int requiredLevel, int minXp, int maxXp) {
        amountOfXpPerLevel.put(requiredLevel, new ExperienceValues(maxXp, minXp));
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
}
