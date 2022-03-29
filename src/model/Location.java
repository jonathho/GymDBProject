package model;

public class Location {
    private final String address;
    private final int gid;
    private final int capacity;
    private final int phone_num;

    public Location(String address, int gid, int capacity, int phone_num) {
        this.address = address;
        this.gid = gid;
        this.capacity = capacity;
        this.phone_num = phone_num;
    }

    public String getAddress() {
        return address;
    }

    public int getGid() {
        return gid;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPhone_num() {
        return phone_num;
    }
}
