package com.synamedia;

import com.nds.cab.crs.domain.data.BookingRequest;
import com.nds.cab.crs.domain.data.Device;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Pvr {
    private int numberOfTuners;
    private Set<Booking> bookings;
    private ITunerConflictDetector tunerConflictDetector = new CRSTunerConflictDetector();

    public Pvr(int numberOfTuners) {
        if (numberOfTuners < 1) {
            throw new IllegalArgumentException("need at least one tuner");
        }
        this.numberOfTuners = numberOfTuners;
        bookings = new HashSet<>();
    }

    public void addBooking(Booking x) {
        if (bookings.contains(x)) {
            throw new IllegalArgumentException("Booking "+ x + " already exists");
        }
        Set<Booking> conflictBookings = getTunerConflicts(x);
        if (conflictBookings.size() > 0) {
            throw new TunerConflictException(conflictBookings);
        }
        bookings.add(x);
    }

    public boolean delBooking(Booking x) {
        return bookings.remove(x);
    }

    /**
     * Return subset of bookings that are conflict with new booking
     * @param booking
     * @return
     */
    public Set<Booking> getTunerConflicts(Booking booking) {
        return tunerConflictDetector.getConflicts(numberOfTuners, bookings, booking);
    }

    public String toString() {
        String s = "Pvr " + numberOfTuners + " tuners\n";
        for (Booking x : bookings) {
            s += x;
            s += "\n";
        }
        return s;
    }
}
