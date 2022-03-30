package model;

import java.sql.Timestamp;

/**
 * Should probably make as interface or superclass later for ClassSession
 */
public class ProjectionClass {
    private final String address;
    private final Timestamp start_time;
    private final String category;
    private final int duration;
    private final int capacity;

    public ProjectionClass(String address, Timestamp start_time, String category,
                           int duration, int capacity) {
        this.address = address;
        this.start_time = start_time;
        this.category = category;
        this.duration = duration;
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public String getCategory() {
        return category;
    }

    public int getDuration() {
        return duration;
    }

    public int getCapacity() {
        return capacity;
    }
}
