package model;

public class ClassesPerLocation {
    private final String name;
    private final String address;
    private final int numClasses;

    public ClassesPerLocation(String name, String address, int numClasses) {
        this.name = name;
        this.address = address;
        this.numClasses = numClasses;
    }

    public String getName(){ return name;}
    public String getAddress() {
        return address;
    }

    public int getNumClasses() {
        return numClasses;
    }
}
