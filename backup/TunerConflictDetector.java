package com.synamedia;

import java.util.Set;

public class TunerConflictDetector implements ITunerConflictDetector {
    /**
     * Detect and return subset of existing bookings that are conflict with new booking
     * @param numberOfTuners
     * @param existingBookings
     * @param newBooking
     * @return set of existing bookings that are conflict with new booking
     */
    @Override
    public Set<Booking> getConflicts(int numberOfTuners, Set<Booking> existingBookings, Booking newBooking) {
        // TODO
        return null;
    }
}
