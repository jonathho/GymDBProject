package model;

public class TotalExerciseTime {
    private final int cid;
    private final int totalExerciseTime;

    public TotalExerciseTime(int cid, int totalExerciseTime) {
        this.cid = cid;
        this.totalExerciseTime = totalExerciseTime;
    }

    public int getCid() {
        return cid;
    }

    public int getTotalExerciseTime() {
        return totalExerciseTime;
    }
}
