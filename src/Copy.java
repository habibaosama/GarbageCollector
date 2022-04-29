import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Copy {

    private ArrayList<Integer> roots ;
    private  ArrayList<Node> heap;
    private HashMap<Integer,Node> heapHash;
    private ArrayList<Node> copied = new ArrayList<>();
    private HashMap<Integer,Integer> hash = new HashMap<>();

    public Copy(String heapPath,String rootPath,String pointersPath){
        this.roots=Files.loadRoot(rootPath);
        this.heap=Files.loadHeapArray(heapPath);
        this.heapHash=Files.convertArrayToHashmap(heap);
        Files.linkNodes(pointersPath,heapHash);
    }

    public ArrayList<Node> copyGC() {
        copyy();
        collect();
        return copied;
    }

    public void copyy(){
        for(int id: roots){
            copied.add(heapHash.get(id));
            hash.put(heapHash.get(id).id,1);
        }
        copy();
    }

    public void copy() {

        for(int i=0;i < copied.size();i++) {
            for (int j = 0; j < copied.get(i).getChildren().size(); j++) {
                Node n = copied.get(i).getChildren().get(j);
                if (hash.get(n.id)==null) {
                    copied.add(n);
                    hash.put(n.id,1);
                }
            }

        }

    }

    public void collect() {
        copied.get(0).setMemory_start(0);
        copied.get(0).setMemory_end(copied.get(0).spaceOccupied-1);
        for (int i = 1; i < copied.size(); i++) {
            copied.get(i).setMemory_start(1 + copied.get(i - 1).getMemory_end());
            copied.get(i).setMemory_end(copied.get(i).getMemory_start() + (copied.get(i).spaceOccupied-1));
        }
    }

    public static void main(String Args[]) throws IOException {
        String[] arg = new String[4];
        arg[0] = System.getProperty("user.dir")+"//src/heap.csv";
        arg[1] =  System.getProperty("user.dir")+"//src/roots.txt";
        arg[2] =System.getProperty("user.dir")+"//src/pointers.csv";;
        arg[3] = System.getProperty("user.dir")+"/src/new-copy.csv";
        System.out.println(arg[1]);
        Copy c =new Copy(arg[0],arg[1],arg[2]);
        Files.heapOut(c.copyGC(), arg[3]);
    }
}
