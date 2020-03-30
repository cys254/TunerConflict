package com.synamedia;

public class Booking {
    private final String id;
    private long startTime;
    private long endTime;
    private int priority;

    public Booking(String id, long startTime, long endTime, int priority) {
        this.id = id;
        if (endTime <= startTime) {
            throw new IllegalArgumentException("end time must be greater than start time");
        }
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Booking) {
            Booking x = (Booking)o;
            // Two booking are consider equal when id equals - even if startTime and duration are different
            return x.id == this.id;
        } else {
            return false;
        }
    }

    public String toString() {
        return id+":[" + startTime + ", " + endTime + "]";
    }
}
