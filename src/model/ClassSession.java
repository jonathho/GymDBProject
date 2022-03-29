package model;

import java.sql.Timestamp;

public class ClassSession {
    private final int class_code;
    private final String address;
    private final int SIN;
    private final Timestamp start_time;
    private final String category;
    private final int duration;
    private final int size;

    public ClassSession(int class_code, String address, int sin, Timestamp start_time, String category,
                        int duration, int size) {
        this.class_code = class_code;
        this.address = address;
        SIN = sin;
        this.start_time = start_time;
        this.category = category;
        this.duration = duration;
        this.size = size;
    }

    public int getClass_code() {
        return class_code;
    }

    public String getAddress() {
        return address;
    }

    public int getSIN() {
        return SIN;
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

    public int getSize() {
        return size;
    }
}
