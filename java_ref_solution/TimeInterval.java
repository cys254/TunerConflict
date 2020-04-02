import java.util.ArrayList;
import java.util.List;

public class TimeInterval implements Comparable<TimeInterval> {
    private long begin;
    private long end;
    List<Booking> bookings = new ArrayList<>();

    TimeInterval(long begin, long end) {
        this.begin = begin;
        this.end = end;
    }

    TimeInterval(long begin, long end, List<Booking> bookings) {
        this.begin = begin;
        this.end = end;
        this.bookings.addAll(bookings);
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    @Override
    public int compareTo(TimeInterval o) {
        return Long.signum(begin - o.end);
    }
}