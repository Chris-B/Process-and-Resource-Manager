package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Resource {
    public Resource(String id) {
        this.ID = id;
        this.Status = new ResourceStatus(GetUnits(id));
        this.Waiting_List = new ArrayList<>();
    }

    static int GetUnits(String id) {
        return Integer.parseInt(id.substring(id.length()-1));
    }

    static Resource GetResource(String id) {
        for (Resources r : Resources.values())
            if(r.resource.ID.equals(id))
                return r.resource;
        return null;
    }

    String ID;
    ResourceStatus Status;
    List<WaitingProcess> Waiting_List;
}

enum Resources {
    R1(new Resource("R1")),
    R2(new Resource("R2")),
    R3(new Resource("R3")),
    R4(new Resource("R4"));
    Resource resource;
    Resources(Resource r) {
        this.resource = r;
    }
}

class WaitingProcess {
    WaitingProcess(Process p, int units) {
        this.process = p;
        this.units = units;
    }
    Process process;
    int units;
}

class ResourceStatus {
    ResourceStatus(int units) {
        this.Units = units;
        this.Type = ResourceStatusType.Free;
    }
    ResourceStatusType Type;
    int Units;
}

enum ResourceStatusType {
    Free,
    Allocated;
}
