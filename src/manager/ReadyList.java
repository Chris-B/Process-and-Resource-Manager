package manager;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class ReadyList {

    static List<Process> RL = new ArrayList<>();

    public static void InsertPCB(Process p) {
        int index = 0;
        for(Process q : RL) {
            if (q.Priority < p.Priority)
                break;
            index++;
        }
        RL.add(index, p);
    }

    public static Process PeekPCB() {
        return RL.get(0);
    }

    public static void RemovePCB(Process p) {
        RL.remove(p);
    }

    public static void Reset() {
        RL.clear();
    }
}
