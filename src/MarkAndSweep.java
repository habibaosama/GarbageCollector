import java.util.ArrayList;
import java.util.HashMap;

public class MarkAndSweep {
    private ArrayList<Integer> roots;
    private  ArrayList<Node> heap;
    private HashMap<Integer,Node> heapHash;

    public MarkAndSweep(String heapPath,String rootPath,String pointersPath){
        this.roots=Files.loadRoot(rootPath);
        this.heap=Files.loadHeapArray(heapPath);
        this.heapHash=Files.convertArrayToHashmap(heap);
        Files.linkNodes(pointersPath,heapHash);
    }

    public ArrayList<Node> marksweep(){
        mark();
        sweep();
        return heap;
    }
    ///////////////////////////////Mark////////////////////////////
    public void mark(){
        for(int id: roots){
            mark(heapHash.get(id));

        }

    }
    public void mark(Node node){
        if(node.isMark())
            return;
        node.setMark(true);
        //loop through all the children of node and mark them true as they are used not garbage
        for(Node child :node.getChildren())
            mark(child);
    }
    ////////////////////////////////////////////Sweep//////////////////////////////////
   /* public void sweep(){
        for(int i=0;i<heap.size();){
           // Node node =heap.get(i);
            if(!heap.get(i).isMark()){
                heap.remove(i);

            }else{
                heap.get(i).setMark(false);
                i++;
            }
            System.out.println(i +" start " +heap.get(i).getMemory_start()+ "end"+heap.get(i).getMemory_end());
        }
    }*/
    public void sweep(){
        for(int i=0;i<heap.size();i++){
            Node node =heap.get(i);
            if(node.isMark()){
                node.setMark(false);
            }
            //if it is not marked ,so it is garbage and remove it and go back again
            else{
                heap.remove(node);
                i--;
            }
        }
    }

    public static void main(String[] args){
       /* String[] arg = new String[4];
        arg[0] = System.getProperty("user.dir")+"\\src\\heap.csv";
        arg[1] =  System.getProperty("user.dir")+"\\src\\roots.txt";
        arg[2] =System.getProperty("user.dir")+"\\src\\pointers.csv";*
        arg[3] = System.getProperty("user.dir")+"\\src\\new-heap-sweep.csv";*/
        System.out.println(args[0]);
        MarkAndSweep mark =new MarkAndSweep(args[0],args[1],args[2]);
        Files.heapOut(mark.marksweep() , args[3]);
    }

}
