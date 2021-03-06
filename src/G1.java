import java.util.ArrayList;
import java.util.HashMap;

public class G1 {
    private ArrayList<Node> heap;
    private HashMap<Integer,Integer> heapp= new HashMap<>();
    private HashMap<Integer,Node> heapHash;
    private ArrayList<Integer> roots = new ArrayList<>();
    private ArrayList<Integer> garbage = new ArrayList<>();
    private ArrayList<Integer>[] array = new ArrayList[16];
    private int[] sizes= new int[16];

    public G1(String heapPath,String rootPath,String pointersPath){
        this.roots=Files.loadRoot(rootPath);
        this.heap=Files.loadHeapArray(heapPath);
        this.heapHash=Files.convertArrayToHashmap(heap);
        Files.linkNodes(pointersPath,heapHash);
    }

    
    
    public ArrayList<Node> G1GC(int size){
        heapp();
        mark();
        G1andDefragment(size);
        sweep();
        return heap;
    }

    public void heapp(){
        for(int i=0;i<heap.size();i++) {
            heapp.put(heap.get(i).id,i);
        }
    }


    public void mark(){
        for(int id: roots){
            mark(heapHash.get(id));
        }

    }
    public void mark(Node node){
        if(node.isMark()) {
            return;
        }
        heap.get(heapp.get(node.id)).setMark(true);
        for(Node child :node.getChildren()) {
            mark(child);
        }
    }

    public  void sweep(){
        for(int i=0;i<heap.size();){
            if(heap.get(i).isMark()==false){
                heap.remove(i);
            }else{
                i++;
            }

        }

    }

    
    public  void G1andDefragment(int size){

        int eachSize = size /16;

        for (int i = 0 ;i < 16;i++){
            sizes[i] = eachSize;
            array[i] = new ArrayList<>();
        }

        for (int i=0;i<heap.size();i++){
                for (int j=heap.get(i).getMemory_start()/eachSize;j<16;j++){
                    if (sizes[j] >= heap.get(i).spaceOccupied){
                        array[j].add(heap.get(i).id);
                        sizes[j] = sizes[j]-heap.get(i).spaceOccupied;
                        break;
                    }
                }
        }
        for (int i = 0; i < 16;i++){
            if (sizes[i] != eachSize){
                for (int j = 0; j < array[i].size(); j++){
                    if (!heap.get(heapp.get(array[i].get(j))).isMark()){
                        sizes[i]+=heap.get(heapp.get(array[i].get(j))).spaceOccupied;
                        array[i].remove(j);
                    }
                }
            }
            if(sizes[i]==eachSize){
                sizes[i]=-1* eachSize;
            }
        }
        //Defragmentation
        for (int i = 0; i < 16; i++){
            if (sizes[i]!=-1*eachSize){
                for (int j = 0 ; j<array[i].size();j++){
                    int s = heap.get(heapp.get(array[i].get(j))).spaceOccupied;
                    for (int k = 0;k<16;k++){
                        if (sizes[k]<0 && (-1*sizes[k])>=s){
                            heap.get(heapp.get(array[i].get(j))).setMemory_start((eachSize+sizes[k]) + k*eachSize);
                            heap.get(heapp.get(array[i].get(j))).setMemory_end(((eachSize+sizes[k]) + k*eachSize) + s-1);
                            sizes[k] =sizes[k]+s;
                            break;
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args){
      /*  String[] arg = new String[5];
        arg[0] = System.getProperty("user.dir")+"//src//heap.csv";
        arg[1] =  System.getProperty("user.dir")+"//src//roots.txt";
        arg[2] =System.getProperty("user.dir")+"//src//pointers.csv";;
        arg[3] = System.getProperty("user.dir")+"//src//new-G1.csv";
        arg[4] = "256";*/
        G1 g=new G1(args[0],args[1],args[2]);
        Files.heapOut(g.G1GC(Integer.parseInt(args[4])) , args[3]);
    }

}
