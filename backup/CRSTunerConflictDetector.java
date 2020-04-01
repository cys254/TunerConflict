package com.synamedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.nds.cab.crs.domain.data.BookingRequest;
import com.nds.cab.crs.CRSFactory;
import com.nds.cab.crs.IConflictDetector;
import com.nds.cab.crs.domain.data.Device;

public class CRSTunerConflictDetector implements ITunerConflictDetector {
    // CRS Tuner Conflict Detector
    private IConflictDetector conflictDetector = CRSFactory.getInstance().getConflictDetector();

    /**
     * Detect and return subset of existing bookings that are conflict with new booking
     * This function is wrapper to CRS tuner conflict detection
     * @param numberOfTuners
     * @param existingBookings
     * @param newBooking
     * @return set of existing bookings that are conflict with new booking
     */
    @Override
    public Set<Booking> getConflicts(int numberOfTuners, Set<Booking> existingBookings, Booking newBooking) {
        // convert existing bookings to CRS BookingRequests
        List<BookingRequest> existingBookingRequests = new ArrayList<>();
        List<Booking> existingBookingList = new ArrayList<>();
        int i = 0;
        for (Booking x : existingBookings) {
            existingBookingList.add(x);
            existingBookingRequests.add(new BookingRequest(i, x.getStartTime(), x.getEndTime() - x.getStartTime(), 0, 0));
            i++;
        }

        // convert new booking to CRS BookingRequest
        int newBookingId = -1;
        BookingRequest newBookingRequest = new BookingRequest(newBookingId, newBooking.getStartTime(), newBooking.getEndTime() - newBooking.getStartTime(), 0, 0);

        // Call CRS tuner conflict detector
        conflictDetector.detectConflicts(newBookingRequest, existingBookingRequests, new Device(1, 1, numberOfTuners));

        // Convert CRS booking conflict requests to list of conflict bookings
        Set<Booking> conflictBookings = new HashSet<>();
        if (newBookingRequest.isConflict()) {
            for (BookingRequest x : newBookingRequest.getBookingConflictRequests()) {
                int id = x.getBookingId();
                if (id >= 0) {
                    conflictBookings.add(existingBookingList.get(id));
                }
            }
        }
        return conflictBookings;
    }
}
