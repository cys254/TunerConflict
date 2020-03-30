package com.synamedia;

import java.util.Set;

public class TunerConflictException extends RuntimeException {
    private Set<Booking> conflicts;

    public TunerConflictException(Set<Booking> conflicts) {
        this.conflicts = conflicts;
    }

    public Set<Booking> getConflicts() {
        return conflicts;
    }

    public String toString() {
        String s = "TunerConflictException\n";
        for (Booking x : conflicts) {
            s  = s + x + "\n";
        }
        return s;
    }
}
