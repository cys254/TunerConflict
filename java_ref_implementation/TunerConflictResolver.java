import java.util.*;

public class TunerConflictResolver {

    private int numberOfTuners;        /// Total number of tuners
    private TunerConflictResult result; /// Tuner conflict detection and resolution result

    private TimeInterval[] timeIntervals;

    /**
     * This function detects tuner conflicts and store conflict resolution sets in resolutions.
     * @return tuner conflict detection and resolution result
     */
    public TunerConflictResult resolve(int numberOfTuners, List<Booking> existingBookings, Booking newBooking) {
        this.numberOfTuners = numberOfTuners;
        this.result = new TunerConflictResult();

        // Implement Tuner Conflict Detection and Resolution here
        List<TimeInterval> timeIntervalList = new LinkedList<>();
        timeIntervalList.add(new TimeInterval(newBooking.getStartTime(), newBooking.getEndTime()));

        for (Booking booking : existingBookings) {
            for (ListIterator<TimeInterval> it = timeIntervalList.listIterator(); it.hasNext();) {
                TimeInterval interval = it.next();
                if (booking.getEndTime() <= interval.getBegin()) {
                    break;
                } else if (booking.getStartTime() < interval.getEnd()) {
                    if (booking.getStartTime() > interval.getBegin()) {
                        TimeInterval leftInterval = new TimeInterval(interval.getBegin(), booking.getStartTime(), interval.getBookings());
                        interval.setBegin(booking.getStartTime());
                        it.previous();
                        it.add(leftInterval);
                        it.next();
                    }
                    if (booking.getEndTime() < interval.getEnd()) {
                        TimeInterval rightInterval = new TimeInterval(booking.getEndTime(), interval.getEnd(), interval.getBookings());
                        interval.setEnd(booking.getEndTime());
                        it.add(rightInterval);
                    }
                    interval.addBooking(booking);
                }
            }
        }

        timeIntervals = timeIntervalList.toArray(new TimeInterval[timeIntervalList.size()]);

        for (int i = 0; i < timeIntervals.length; i++) {
            List<Booking> bookings = timeIntervals[i].getBookings();
            if (bookings.size() < numberOfTuners) {
                continue;
            }
            result.setConflict(true);
            for (Booking booking: bookings) {
                ConflictResolutionSet resolution = new ConflictResolutionSet(booking);
                checkNextConflictInterval(resolution, i);
            }
            break;
        }

        return result;
    }

    private void checkNextConflictInterval(ConflictResolutionSet resolution, int prevConflictIndex) {
        for (int i = prevConflictIndex+1; i < timeIntervals.length; i++) {
            if (timeIntervals[i].getBookings().size() < numberOfTuners) {
                continue;
            }
            if (timeIntervals[i].getEnd() <= resolution.getLastBooking().getEndTime()) {
                continue;
            }
            for (Booking booking: timeIntervals[i].getBookings()) {
                if (booking.getStartTime() < timeIntervals[prevConflictIndex].getEnd()) {
                    continue;
                }
                checkNextConflictInterval(new ConflictResolutionSet(resolution, booking), i);
            }
            return;
        }
        result.addResolution(resolution);
    }
}