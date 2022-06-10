package be.elgem.Jobs.Misc;

public class Level {
    private double experienceGrowth;

    private int maxLevelExperience;
    private int experience;

    private int initialMaxExperience;

    private int maxLevel;
    private int level;

    public Level(int level, int maxLevel, int experience, int initialMaxExperience, double experienceGrowth) {
        this.experience = experience;
        this.level = level;
        this.maxLevel = maxLevel;
        this.initialMaxExperience = initialMaxExperience;
        this.experienceGrowth = experienceGrowth;

        computeMaxExperience();
    }

    private void computeMaxExperience() {
        this.maxLevelExperience = (int) (initialMaxExperience + experienceGrowth * level);
    }

    /**
     * Add experience to the current job
     * @param addedExperience Added experience
     */
    public void addExperience(int addedExperience) {
        experience += addedExperience;

        if(addedExperience>0) {
            checkForLevelUp();
        }
        else{
            checkForLevelDown();
        }
    }

    /**
     * Check for a level up
     */
    private void checkForLevelUp() {
        if(experience > maxLevelExperience){
            if(levelUp()) {
                experience -= maxLevelExperience;

                computeMaxExperience();

                checkForLevelUp();
            }
            else{
                experience = maxLevelExperience;
            }
        }
    }

    /**
     * Check for a level down
     */
    private void checkForLevelDown() {
        if(experience < 0){
            if(levelDown()) {
                computeMaxExperience();

                experience = maxLevelExperience + experience;

                checkForLevelDown();
            }
            else{
                experience = 0;
            }
        }
    }

    /**
     * Level up the job
     * @return True if the job successfully leveled up
     */
    private boolean levelUp(){
        if(level + 1 > maxLevel) {
            return false;
        }
        else{
            level++;
            return true;
        }
    }

    /**
     * Level down the job
     * @return True if the job successfully leveled down
     */
    private boolean levelDown() {
        if(level - 1 <= 0) {
            return false;
        }
        else{
            level--;
            return true;
        }
    }

    /**
     * @return Current experience of the job
     */
    public int getExperience() {
        return this.experience;
    }

    /**
     * @return Maximum amount of xp to level up
     */
    public int getMaxLevelExperience() {
        return this.maxLevelExperience;
    }

    /**
     * @return Current level of the job
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * @return Maximum level of the job
     */
    public int getMaxLevel() {
        return this.maxLevel;
    }
}
