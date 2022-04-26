import java.util.ArrayList;
import java.util.HashMap;

public class MarkAndCompact {
    private ArrayList<Integer> roots;
    private  ArrayList<Node> heap;
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
   private void mark(){
       for(int id: roots){
           markNode(heapHash.get(id));

       }

   }
   private void markNode(Node node){
       if(node.isMark())
           return;
       node.setMark(true);
       //loop through all the children of node and mark them true as they are used not garbage
       for(Node child :node.getChildren())
           markNode(child);
   }
   ///////////////////////////////////////////////////Compact/////////////////////////////////////
    private void compact(){

    }
}
