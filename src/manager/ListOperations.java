package manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ListOperations {

    static Process GetProcess(Process head, String pid) {
        if (head == null)
            return null;
        if (head.ID.equals(pid)) {
            return head;
        }
        for (Process p : head.Creation_Tree.Child) {
            Process q = GetProcess(p,pid);
            if (q != null)
                return q;
        }
        return null;
    }

    static Other_Resource GetResource(LinkedList<Other_Resource> resources, String rid) {
        for (Other_Resource or : resources) {
            if (or.resource.ID.equals(rid))
                return or;
        }
        return null;
    }

    static WaitingProcess GetWaitingProcess(List waiting, Process p) {
        for (Object o : waiting) {
            WaitingProcess wp = (WaitingProcess)o;
            if (wp.process == p)
                return wp;
        }
        return null;
    }

}
