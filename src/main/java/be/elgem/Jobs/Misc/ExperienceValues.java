package be.elgem.Jobs.Misc;

public class ExperienceValues {
    private int min;
    private int max;

    public ExperienceValues(int min, int max) {
        if(min < max){
            this.min = min;

            this.max = max;
        }
        else{
            this.min = max;

            this.max = min;
        }

    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
