package main

import (
	"container/list"
	"fmt"
	"time"
)

type TunerConflictResult struct {
	HasConflict bool
	Resolutions []*ConflictResolutionSet
}

type TunerConflictResolver struct {
	numberOfTuners int
	Result TunerConflictResult
}

func (r *TunerConflictResolver) ResolveTunerConflict(numberOfTuners int, existingBookings []*Booking, newBooking *Booking) TunerConflictResult {
	r.numberOfTuners = numberOfTuners

	// Implement Tuner Conflict Detection and Resolution here

	return r.Result
}

func (r *TunerConflictResolver) resolveMoreInterval(resolution ConflictResolutionSet, e *list.Element, prevConflict time.Time) {
	lastBooking := resolution[len(resolution)-1]
	for ; e != nil; e = e.Next() {
		interval := e.Value.(*TimeInterval)
		if len(interval.bookings) < r.numberOfTuners {
			continue
		}
		if !interval.endTime.After(lastBooking.EndTime) {
			continue
		}
		for _, booking := range interval.bookings {
			if booking.StartTime.After(prevConflict) {
				r.resolveMoreInterval(append(resolution, booking), e.Next(), interval.startTime)
			}
		}
		return
	}
	r.Result.Resolutions = append(r.Result.Resolutions, &resolution)
}

func PrintTunerConflictResult(result TunerConflictResult) {
	for _, crs := range result.Resolutions {
		fmt.Println(crs.String())
	}
}
