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

	// General Time Intervals with bookings per Interval
	var intervalList list.List
	intervalList.PushBack(&TimeInterval{startTime: newBooking.StartTime, endTime: newBooking.EndTime})
	for _, booking := range existingBookings {
		if booking.StartTime.Before(newBooking.EndTime) && booking.EndTime.After(newBooking.StartTime) {
			var next *list.Element
			for e := intervalList.Front(); e != nil; e = next {
				interval := e.Value.(*TimeInterval)
				next = e.Next()
				if ! interval.startTime.Before(booking.EndTime) {
					break
				} else if interval.endTime.After(booking.StartTime) {
					if interval.startTime.Before(booking.StartTime) {
						intervalList.InsertBefore(&TimeInterval{startTime: interval.startTime, endTime: booking.StartTime, bookings: interval.bookings}, e)
						interval.startTime = booking.StartTime
					}
					if booking.EndTime.Before(interval.endTime) {
						intervalList.InsertAfter(&TimeInterval{startTime: booking.EndTime, endTime: interval.endTime, bookings: interval.bookings}, e)
						interval.endTime = booking.EndTime
					}
					interval.bookings = append(interval.bookings, booking)
				}
			}
		}
	}

	for e := intervalList.Front(); e != nil; e = e.Next() {
		interval := e.Value.(*TimeInterval)
		if len(interval.bookings) < r.numberOfTuners {
			continue
		}
		r.Result.HasConflict = true
		for _, booking := range interval.bookings {
			r.resolveMoreInterval(ConflictResolutionSet{booking}, e.Next(), interval.startTime)
		}
		break
	}

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
