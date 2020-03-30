package com.synamedia;

import java.util.Set;

public interface ITunerConflictDetector {
    /**
     * Detect and return subset of existing bookings that are conflict with new booking
     * @param numberOfTuners
     * @param existingBookings
     * @param newBooking
     * @return set of existing bookings that are conflict with new booking
     */
    Set<Booking> getConflicts(int numberOfTuners, Set<Booking> existingBookings, Booking newBooking);
}
