package manager;

import java.util.Comparator;

public class ProcessComparator implements Comparator<Process> {
    public int compare(Process s1, Process s2) {
        return s2.Priority - s1.Priority;
    }
}
