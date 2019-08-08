package manager;

import file.FileManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// manager.Process Control Block
public class Process {

    public Process(String ID, Process parent, int priority) {
        this.ID = ID;
        Other_Resources = new LinkedList<>();
        Status = new ProcessStatus();
        Creation_Tree = new Creation_Tree(parent);
        this.Priority = priority;
    }

    public void Create(String ID, int priority) {
        Process process = new Process(ID, this, priority);
        this.Creation_Tree.Child.add(process);
        ReadyList.InsertPCB(process);
    }

    public boolean Request(String rid, int units) {
        Resource r = Resource.GetResource(rid);
        if(r == null || units > Resource.GetUnits(rid)) {
            FileManager.write(" error");
            return false;
        }
        if(r.Status.Type == ResourceStatusType.Free && r.Status.Units >= units) {
            r.Status.Units -= units;
            if(r.Status.Units == 0)
                r.Status.Type = ResourceStatusType.Allocated;
            Other_Resource or = ListOperations.GetResource(Other_Resources, rid);
            if(or == null)
                this.Other_Resources.add(new Other_Resource(r, units));
            else
                or.units+=units;
        } else {
            this.Status.Type = ProcessStatusType.Blocked;
            this.Status.List = r.Waiting_List;
            ReadyList.RemovePCB(this);
            r.Waiting_List.add(new WaitingProcess(this, units));
        }
        return true;
    }

    public boolean Release(String rid, int units) {
        Other_Resource or = ListOperations.GetResource(Other_Resources, rid);
        if(or == null) {
            FileManager.write(" error");
            return false;
        }
        if(units > or.units || units < 0) {
            FileManager.write(" error");
            return false;
        }
        Resource r = or.resource;
        r.Status.Units += units;
        or.units -= units;
        if(or.units == 0)
            Other_Resources.remove(or);
        if(r.Status.Units > 0) {
            r.Status.Type = ResourceStatusType.Free;
            if (!r.Waiting_List.isEmpty()) {
                WaitingProcess wp = r.Waiting_List.get(0);
                if(wp.units > r.Status.Units)
                    return true;
                r.Waiting_List.remove(wp);
                Process q = wp.process;
                q.Status.Type = ProcessStatusType.Ready;
                q.Status.List = ReadyList.RL;
                q.Request(r.ID, wp.units);
                ReadyList.InsertPCB(q);
            }
        }
        return true;
    }

    public void ClearResources() {
        for (Other_Resource or : Other_Resources) {
            Resource r = or.resource;
            Release(r.ID, or.units);
        }
    }

    String ID;
    LinkedList<Other_Resource> Other_Resources;
    ProcessStatus Status;
    Creation_Tree Creation_Tree;
    int Priority;
}

class Other_Resource {
    Other_Resource(Resource r, int units) {
        this.resource = r;
        this.units = units;
    }
    Resource resource;
    int units;
}

// manager.Process Status Structure
class ProcessStatus {

    ProcessStatus() {
        Type = ProcessStatusType.Ready;
        List = ReadyList.RL;
    }

    ProcessStatusType Type;
    List List;
}

// manager.Process Status States
enum ProcessStatusType {
    Ready,
    Running,
    Blocked
}

// Maintain link to parent and sub-processes
// Used to destroy subtree
class Creation_Tree {

    Creation_Tree(Process parent) {
        this.Parent = parent;
        this.Child = new LinkedList<>();
    }

    Process Parent;
    LinkedList<Process> Child;
}