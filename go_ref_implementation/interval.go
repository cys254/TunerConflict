package main

import (
	"strconv"
	"time"
)

type TimeInterval struct {
	startTime time.Time
	endTime time.Time
	bookings []*Booking
}

func (i *TimeInterval) String() string {
	return "[" + i.startTime.Format(time.RFC3339) + "," + i.endTime.Format(time.RFC3339) + "]:" + strconv.Itoa(len(i.bookings))
}
