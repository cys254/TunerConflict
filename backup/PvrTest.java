package com.synamedia;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PvrTest {
    @org.junit.jupiter.api.Test
    void test001_one_tuner_one_booking() {
        Pvr pvr = new Pvr(1);
        Booking booking1 = new Booking("1", 0, 1, 0);
        Set<Booking> conflicts = pvr.getTunerConflicts(booking1);
        assertEquals(0, conflicts.size());
    }

    @org.junit.jupiter.api.Test
    void test003_one_tuner_two_bookings_no_conflict() {
        Pvr pvr = new Pvr(1);
        Booking booking1 = new Booking("1", 0, 1, 0);
        Booking booking2 = new Booking("2", 1, 2, 0);
        pvr.addBooking(booking1);
        Set<Booking> conflicts = pvr.getTunerConflicts(booking2);
        assertEquals(0, conflicts.size());
    }

    @org.junit.jupiter.api.Test
    void test003_one_tuner_two_bookings_full_conflict() {
        Pvr pvr = new Pvr(1);
        Booking booking1 = new Booking("1", 0, 1, 0);
        Booking booking2 = new Booking("2", 0, 1, 0);
        pvr.addBooking(booking1);
        Set<Booking> conflicts = pvr.getTunerConflicts(booking2);
        assertEquals(new HashSet(Arrays.asList(booking1)), conflicts);
    }

    @org.junit.jupiter.api.Test
    void test004_one_tuner_two_bookings_partial_conflict_1() {
        Pvr pvr = new Pvr(1);
        Booking booking1 = new Booking("1", 0, 2, 0);
        Booking booking2 = new Booking("2", 1, 2, 0);
        pvr.addBooking(booking1);
        Set<Booking> conflicts = pvr.getTunerConflicts(booking2);
        assertEquals(new HashSet(Arrays.asList(booking1)), conflicts);
    }

    @org.junit.jupiter.api.Test
    void test005_one_tuner_two_bookings_partial_conflict_2() {
        Pvr pvr = new Pvr(1);
        Booking booking1 = new Booking("1", 1, 2, 0);
        Booking booking2 = new Booking("2", 0, 2, 0);
        pvr.addBooking(booking1);
        Set<Booking> conflicts = pvr.getTunerConflicts(booking2);
        assertEquals(new HashSet(Arrays.asList(booking1)), conflicts);
    }

    @org.junit.jupiter.api.Test
    void test005_two_tuner_two_bookings_no_conflict_1() {
        Pvr pvr = new Pvr(2);
        Booking booking1 = new Booking("1", 0, 1, 0);
        Booking booking2 = new Booking("2", 0, 1, 0);
        pvr.addBooking(booking1);
        Set<Booking> conflicts = pvr.getTunerConflicts(booking2);
        assertEquals(0, conflicts.size());
    }

    @org.junit.jupiter.api.Test
    void test006_two_tuner_two_bookings_no_conflict_2() {
        Pvr pvr = new Pvr(2);
        Booking booking1 = new Booking("1", 0, 1, 0);
        Booking booking2 = new Booking("2", 1, 2, 0);
        Booking booking3 = new Booking("3", 0, 2, 0);
        pvr.addBooking(booking1);
        pvr.addBooking(booking2);
        Set<Booking> conflicts = pvr.getTunerConflicts(booking3);
        assertEquals(0, conflicts.size());
    }

    @org.junit.jupiter.api.Test
    void test007_two_tuner_two_bookings_with_conflict_1() {
        Pvr pvr = new Pvr(2);
        Booking booking1 = new Booking("1", 0, 1, 0);
        Booking booking2 = new Booking("1", 1, 3, 0);
        Booking booking3 = new Booking("2", 2, 4, 0);
        Booking booking4 = new Booking("3", 1, 4, 0);
        pvr.addBooking(booking1);
        pvr.addBooking(booking2);
        pvr.addBooking(booking3);
        Set<Booking> conflicts = pvr.getTunerConflicts(booking4);
        assertEquals(new HashSet(Arrays.asList(booking2, booking3)), conflicts);
    }

    @org.junit.jupiter.api.Test
    void test008_two_tuner_two_bookings_with_conflict_2() {
        Pvr pvr = new Pvr(2);
        Booking booking1 = new Booking("1", 0, 1, 0);
        Booking booking2 = new Booking("1", 1, 3, 0);
        Booking booking3 = new Booking("2", 2, 4, 0);
        Booking booking4 = new Booking("3", 0, 4, 0);
        pvr.addBooking(booking1);
        pvr.addBooking(booking2);
        pvr.addBooking(booking3);
        Set<Booking> conflicts = pvr.getTunerConflicts(booking4);
        assertEquals(new HashSet(Arrays.asList(booking2, booking3)), conflicts);
    }
}