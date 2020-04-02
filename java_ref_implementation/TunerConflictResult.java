import java.util.ArrayList;
import java.util.List;

public class TunerConflictResult {
    private boolean hasConflict;
    private List<ConflictResolutionSet> resolutions = new ArrayList<>();

    public boolean hasConflict() {
        return hasConflict;
    }

    public void setConflict(boolean hasConflict) {
        this.hasConflict = hasConflict;
    }

    public List<ConflictResolutionSet> getResolutions() {
        return resolutions;
    }

    public void addResolution(ConflictResolutionSet resolution) {
        resolutions.add(resolution);
    }

    public void print() {
        System.out.println("Number of conflict resolutions: " + resolutions.size());
        for (ConflictResolutionSet resolution : resolutions) {
            System.out.println(resolution);
        }
    }
}