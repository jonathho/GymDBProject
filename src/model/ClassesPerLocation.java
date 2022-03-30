package model;

public class ClassesPerLocation {
    private final String address;
    private final int numClasses;

    public ClassesPerLocation(String address, int numClasses) {
        this.address = address;
        this.numClasses = numClasses;
    }

    public String getAddress() {
        return address;
    }

    public int getNumClasses() {
        return numClasses;
    }
}
