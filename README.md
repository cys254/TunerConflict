# Tuner Conflict Resolution

## 1 Problem Statements

Personal video recorder (Pvr) is responsible for manage bookings
(i.e., requests to make recordings)

A pvr has given amount of tuners and each recording needs a tuner.
This limit the amount of concurrent recordings a Pvr can make.
For example, if a Pvr only has one tuner, it can only record one program at a time.

When a PVR runs out of tuners making recording, we call this situation *tuner conflict*.
Our goal is to check for *tuner conflict* when a booking is made.

We have two goals

1. Tuner Conflict Detection: Predict if *tuner conflict* will happen when a booking is made,
given the number of tuners a Pvr has and bookings that have already been made before.
2. Tuner Conflict Resolution: When *tuner conflict* is detected, propose to user a list of
existing bookings that can be removed to free up tuner for new bookings.

## 2 Key Concept

### 2.1 Booking

**Booking** is a request to make a recording.

A booking can have many other properties.
Among them, *start time* and *end time* are used during to detect and resolve tuner conflict.

In this document, booking is denoted as a time interval.
For example `[0, 1]` means a booking with start time be 0 and end time be 1.

We allow multiple bookings with same start time and end time.
They are still considered different bookings.

### 2.2 Pvr

**Pvr** is personal video recorder responsible for managing booking and making recording.

A Pvr has many properties, among them, *number of tuners* and *set of existing bookings*
are used to detect and resolve tuner conflict.

### 2.3 Tuner Conflict

**Tuner conflict** is a situation when there is no enough tuners to make concurrent recordings.

### 2.4 Tuner Conflict Detection

**Tuner Conflict Detection** is a process to detect tuner conflict.
In this context we are only concerned about tuner conflict detection at booking time, i.e.,
detect tuner conflict when a new booking is made.

Tuner conflict detection can be denoted mathematically:
```cassandraql
F: (total number of tuners, set of existing bookings, new booking to be made) -> boolean
```

For example:
```cassandraql
F: (1, {[0, 1]}, [0, 1]) -> true
```
means when there is only one tuner and a booking from 0 to 1 is already made,
a new booking from 0 to 1 will result in tuner conflict.

### 2.5 Conflict Resolution Set

**Conflict Resolution Set** is subset of existing bookings such that when *all* bookings in
the *Conflict Resolution Set* are deleted, the new booking can be made without tuner conflict.

A conflict resolution set can have one or more bookings.
When conflict resolution contains more than one bookings, all of them need be deleted to resolve tuner conflict.

For example, if a PVR has one tuner, and two existing bookings are made from 0 to 1 and from 1 to 2 

### 2.6 Minimal Conflict Resolution Set

A **Minimal Conflict Resolution Set** is a **Conflict Resolution Set** that
none of its proper subset is a **Conflict Resolution Set**.

That is, minimal conflict resolution set is smallest conflict resolution set.

Minimal conflict resolution set for tuner conflict resolution is not unique.
That is, there can be more than one minimal conflict resolution set for a given tuner conflict resolution.

### 2.7 Tuner Conflict Resolution Set

**Tuner Conflict Resolution Set** is the set of all **Minimal Conflict Resolution Sets** for a given tuner conflict.

When there is no tuner conflict, tuner conflict resolution set is an empty set.

### 2.8 Tuner Conflict Resolution
**Tuner Conflict Resolution** is a process to generate tuner conflict resolution set for a given tuner conflict.
That is:
```cassandraql
F: (total number of tuners, set of existing bookings, new booking to be made) -> tuner conflict resolution set
```
For example:
```cassandraql
F: (1, {[0, 1]}, [0, 2]) -> {{[0, 1]}}
```
A more complicate example:
```cassandraql
F: (2, {[0,3],[2,5],[4,7],[6,9]}, [0,9])
    -> {{[0,3],[4,7]},{[2,5],[4,7]},{[2,5],[6,9]}}
```
The above example means that if there are two tuners and four existing bookings from
1. 0 to 3
2. 2 to 5
3. 4 to 7
4. 6 to 9
Then in order to make a new booking from 0 to 9,
there are three distinct ways to resolve tuner conflicts.
1. delete existing bookings from 0 to 3 and from 4 to 7.
2. delete existing bookings from 2 to 5 and from 4 to 7.
3. delete existing bookings from 2 to 5 and from 6 to 9.

### 2.9 Tuner Conflict Resolution With Priority (Optional)
When each booking is assigned a priority,
only bookings with lower priority can be deleted to resolve tuner conflict for higher priority booking.

This way tuner conflict resolution can be done automatically:
1. Each *conflict resolution set* is also assigned a priority,
defined as maximum priority of all bookings in the set.
2. *Tuner conflict resolution set* is sorted by priority.
2. *Tuner conflict resolution set* only contains *conflict resolution set* with priority lower or equal to new booking priority.
3. If all *conflict resolution set* has higher priority than new booking priority,
*tuner conflict resolution set* will be an empty set.

## 3 Implementation

Implement functions for tuner conflict detection and resolution.
Detection and resolution can be done in single step or separately.

The function should take the following parameters:
1. total number of tuners - which should be an positive integer
2. existing bookings - as any collection of bookings
3. new booking - which should be a single booking

Tuner conflict detection result should be boolean, with `true` interpreted as having tuner conflict.

Tuner conflict resolution result should be a tuner conflict resolution set, as collection of conflict resolution set.
The result should be empty when there is no tuner conflict.

Sample implementation of booking and conflict resolution set in `Java` and `Go` are provided for convenience. 

### 3.1 Java Implementations

#### 3.1.1 Classes Already Implemented

##### Booking
A booking contains the following properties:
- startTime
- endTime
- priority

Two booking objects are considered different even if their properties are all same.

##### ConflictResolutionSet
Conflict resolution set is set of bookings implemented using Java ArrayList.
It also has a priority that is defined as max priority of all bookings in the set.

##### TunerConflictResult
Result of tuner conflict detection and resolution, it has two properties:
- hasConflict as boolean
- resolutions as array list of conflict resolution set

#### 3.1.2 Task
A template *TunerConflictResolver.java* is provided.
Use it to fill in the implementation of tuner conflict detection and resolution:
```
    public TunerConflictResult resolve(int numberOfTuners, List<Booking> existingBookings, Booking newBooking) {
        this.numberOfTuners = numberOfTuners;
        this.result = new TunerConflictResult();

        // Implement Tuner Conflict Detection and Resolution here
...
        return result;
    }
```

#### 3.1.3 Test
Several unit test cases are provided in *TunerConflictResolverTest.java*

### 3.2 Go Implementation


 
