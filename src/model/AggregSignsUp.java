package model;

public class AggregSignsUp {
    private final int cid;
    private final int numClasses;

    public AggregSignsUp(int cid, int numClasses) {
        this.cid = cid;
        this.numClasses = numClasses;
    }

    public int getCid() {
        return cid;
    }

    public int getNumClasses() {
        return numClasses;
    }
}
