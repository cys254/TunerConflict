package main

import "time"

type Booking struct {
	StartTime time.Time
	EndTime time.Time
	Priority int
}

func (b *Booking) String() string {
	return "[" + b.StartTime.Format(time.RFC3339) + "," + b.EndTime.Format(time.RFC3339) + "]"
}