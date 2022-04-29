import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MarkAndCompact {
    private ArrayList<Integer> roots;
    private  ArrayList<Node> heap=new ArrayList<>();
    private HashMap<Integer,Node> heapHash;

   public MarkAndCompact(String heapPath,String rootPath,String pointersPath){
       this.roots=Files.loadRoot(rootPath);
       this.heap=Files.loadHeapArray(heapPath);
       this.heapHash=Files.convertArrayToHashmap(heap);
        Files.linkNodes(pointersPath,heapHash);
   }
   public ArrayList<Node> markCompact(){
       mark();
       compact();
       return heap;
   }
   //method for mark the nodes
   /////////////////////////////////Mark////////////////////////////////////////
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
   ///////////////////////////////////////////////////Compact/////////////////////////////////////
    private void compact(){
     int m=0;
        Collections.sort(heap);
     for(int i=0;i<heap.size();i++){
         Node node =heap.get(i);
         if(node.isMark()){
             node.setMemory_start(m);
             node.setMemory_end(m+ node.getSpaceOccupied()-1);
             m+=node.getSpaceOccupied();
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
       /*String[] arg = new String[4];
        arg[0] = System.getProperty("user.dir")+"\\src\\case1\\"+args[0];
        arg[1] =  System.getProperty("user.dir")+"\\src\\case1\\"+args[1];
        arg[2] =System.getProperty("user.dir")+"\\src\\case1\\"+args[2];
        arg[3] = System.getProperty("user.dir")+"\\src\\case1\\"+args[3];;*/

        MarkAndCompact mark =new MarkAndCompact(args[0],args[1],args[2]);
        Files.heapOut(mark.markCompact() , args[3]);
    }
}
