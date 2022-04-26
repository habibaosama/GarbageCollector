import java.util.ArrayList;

public class Node {
    int  id;
    int spaceOccupied;
    int memory_start;
    int memory_end;
    boolean mark;
    int size;
    ArrayList<Node> children=new ArrayList<>();

    Node(int id,int start,int end){
        this.id=id;
        this.memory_start=start;
        this.memory_end =end;
        this.spaceOccupied=end-start+1;
        this.size=this.memory_end-this.memory_start;
        mark=false;
    }

    public int getMemory_end() {
        return memory_end;
    }

    public void setMemory_end(int memory_end) {
        this.memory_end = memory_end;
    }
    public int getMemory_start() {
        return memory_start;
    }

    public void setMemory_start(int memory_start) {
        this.memory_start = memory_start;
    }
    public int getSpaceOccupied() {
        return spaceOccupied;
    }

    public void setSpaceOccupied(int spaceOccupied) {
        this.spaceOccupied = spaceOccupied;
    }
    public int getId() {
        return id;
    }
    public ArrayList<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }
    public boolean isMark() {
        return mark;
    }
    public int getSize(){
        return size;
    }
    public void setMark(boolean mark) {
        this.mark = mark;
    }





}
