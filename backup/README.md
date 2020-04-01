# TunerConflict

## Problem Statement
We are implementing a personal video recorder (Pvr) that is responsible for manage bookings (requests to make a recording).
A pvr has said amount of tuners and each recording needs a tuner.
This limit the amount of concurrent recordings a Pvr can make.
For example, if a Pvr only has one tuner, an existing booking has been made from 0 to 2,
then a new booking from 1 to 3 will conflict existing booking therefore can not be fulfilled.
We call this a tuner conflict.

The goal is to check for tuner conflict when a booking is made.

## Classes Already Implemented
### Pvr
A Pvr represented personal video recorder, it has the following properties:
- numberOfTuner
- set of existing bookings
It also implements the following methods
- addBooking: to add a new booking
- getTunerConflicts: this method should return a subset of existing bookings that is conflict with new booking.
If there is no conflict, this method should return an empty set. The implementation of this method is in another class.

### Booking
A booking contains the following properties:
- id
- startTime
- endTime

id is unique across all bookings made to same Pvr. End time must be greater than start time.

### CRSTunerConflictDetector
This class contains a wrapper method to CRS implementation of tuner conflict detector.
We have lost source codes to the CRS implementation and now only a jar file contains java classes is available.
The name of the java file is `crs-core-0.0.2-5.jar`.

## Task
The task is to implement a new method to detect tuner conflict.
The template of this method is already provided in `TunerConflictDetector.java`
```
    /**
     * Detect and return subset of existing bookings that are conflict with new booking
     * @param numberOfTuners
     * @param existingBookings
     * @param newBooking
     * @return set of existing bookings that are conflict with new booking
     */
    @Override
    public Set<Booking> getConflicts(int numberOfTuners, Set<Booking> existingBookings, Booking newBooking) {
        // TODO
        return null;
    }
```

## Instruction

### Implementation

Clone this repo and open it with intelliJ.

Existing codes using CRS implementation. Your task is to finish your own implementation in `TunerConflictDetector.java` and
replace the CRS implementation by changing the following line in `Pvr.java` from
```
    private ITunerConflictDetector tunerConflictDetector = new CRSTunerConflictDetector();
```
to
```
    private ITunerConflictDetector tunerConflictDetector = new TunerConflictDetector();
```

### Test
Some unit test cases are implemented in `PvrTest.java`. You can also learn how to write new test cases using existing example.

Note the CRS implementation actually failed the one test cases. That is, if there are two tuners and three existing bookings,
(as a short-cut, we denote a booking as [startTime, endTime]).
- [0, 1]
- [1, 3]
- [2, 4]
Then a new booking [0, 4] should conflict with [1, 3] and [2, 4] only.
However, CRS implementation of tuner conflict detector returns [0, 1] as well.

Your implementation is expected to fix this bug.
