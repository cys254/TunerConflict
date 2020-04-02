import java.util.Objects;

public class Booking {
    private long startTime;
    private long endTime;
    private int priority;

    public Booking(long startTime, long endTime, int priority) {
        if (endTime <= startTime) {
            throw new IllegalArgumentException("endTime must be greater than startTime");
        }
        if (priority < 0) {
            throw new IllegalArgumentException("priority must be greater or equal to zero");
        }
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
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
    public String toString() {
        return super.toString() + ":[" + startTime + "," + endTime + "]:" + priority;
    }
}