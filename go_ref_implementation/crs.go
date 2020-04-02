package main

import "strconv"

type ConflictResolutionSet []*Booking

func (s *ConflictResolutionSet) String() string {
	result := ""
	for i, booking := range *s {
		result += strconv.Itoa(i) + ":" + booking.String() + "\n"
	}
	return result
}

func (s *ConflictResolutionSet) GetPriority() int {
	var priority int
	for _, booking := range *s {
		if booking.Priority > priority {
			priority = booking.Priority
		}
	}
	return priority
}