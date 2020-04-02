package main

import (
	"testing"
	"time"
)

func intToTime(x int) time.Time {
	return time.Date(2020, 4, 1, 20, x, 0, 0, &time.Location{})
}

func Test001OneTunerOneBooking(t *testing.T) {
	resolver := TunerConflictResolver{}

	result := resolver.ResolveTunerConflict(1, []*Booking{}, &Booking{intToTime(0), intToTime(1), 0})

	if result.HasConflict {
		t.Errorf("Tuner Conflict Result: expected %v got %v", false, result.HasConflict)
	}
}

func Test100TwoTunerFourBookingWithConflict(t *testing.T) {
	booking1 := &Booking{intToTime(0), intToTime(3), 0}
	booking2 := &Booking{intToTime(2), intToTime(5), 0}
	booking3 := &Booking{intToTime(4), intToTime(7), 0}
	booking4 := &Booking{intToTime(6), intToTime(9), 0}
	existingBookings := []*Booking{booking1, booking2, booking3, booking4}
	newBooking := &Booking{intToTime(0), intToTime(9), 0}

	resolver := TunerConflictResolver{}
	result := resolver.ResolveTunerConflict(2, existingBookings, newBooking)

	PrintTunerConflictResult(result)

	if result.HasConflict != true {
		t.Errorf("Tuner Conflict Result: expected %v got %v", true, result.HasConflict)
	}
}
