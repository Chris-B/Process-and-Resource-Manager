package manager;

import file.FileManager;

public class Manager {

    public Manager() {
        Init();
    }

    public void Init() {
        KillTree(head);
        ReadyList.Reset();
        head = new Process("init", null, 0);
        head.Status.Type = ProcessStatusType.Running;
        running = head;
        ReadyList.InsertPCB(head);
        FileManager.write("init");
    }

    public void Create(String id, int priority) {
        running.Create(id, priority);
        Scheduler(1);
    }

    public void Destroy(String id) {
        Process p = ListOperations.GetProcess(running, id);
        if(p == null) {
            FileManager.write(" error");
            return;
        }
        KillTree(p);
        if(running == p)
            running = null;
        Scheduler(3);
    }

    void KillTree(Process p) {
        if (p == null)
            return;
        for (Process q : p.Creation_Tree.Child) {
            KillTree(q);
        }
        p.Creation_Tree.Child.clear();
        if(p.Status.List == ReadyList.RL)
            ReadyList.RemovePCB(p);
        else
            p.Status.List.remove(ListOperations.GetWaitingProcess(p.Status.List, p));
        p.ClearResources();
    }

    public void Request(String rid, int units) {
        if(running == head) {
            FileManager.write(" error");
            return;
        }
        if(running.Request(rid, units))
            Scheduler(2);
    }

    public void Release(String rid, int units) {
        if(running.Release(rid, units))
            Scheduler(1);
    }

    public void Timeout() {
        Process q = running;
        ReadyList.RemovePCB(q);
        q.Status.Type = ProcessStatusType.Ready;
        ReadyList.InsertPCB(q);
        Scheduler(2);
    }



    public void Scheduler(int condition) {
        Process p = ReadyList.PeekPCB();
        boolean preempt = false;
        if(condition == 1 && running.Priority < p.Priority)
            preempt = true;
        if(condition == 2 && running.Status.Type != ProcessStatusType.Running)
            preempt = true;
        if(condition == 3 && running == null)
            preempt = true;
        if(preempt) {
            p.Status.Type = ProcessStatusType.Running;
            running = p;
        }
        FileManager.write(" "+running.ID);
    }

    public Process running;
    public Process head;

}