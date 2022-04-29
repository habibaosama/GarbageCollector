import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Files {

    public static ArrayList<Integer> loadRoot(String rootPath){
        ArrayList<Integer> root = new ArrayList<Integer>();
        String line="";
        try{
            BufferedReader reader=new BufferedReader(new FileReader(rootPath));
            while((line=reader.readLine())!=null)
                root.add(Integer.parseInt(line));
            reader.close();

        }catch (Exception e){
           System.out.println("Can't open root file");
        }
       return root;
    }
    public static ArrayList<Node> loadHeapArray(String heapPath){
        ArrayList<Node> heapArray = new ArrayList<Node>();
        String line;
        try{
            BufferedReader reader=new BufferedReader(new FileReader(heapPath));
            while((line=reader.readLine())!=null){
                String [] info=line.split(",");
                //to replace all non digit numbers with space
                info[0]=info[0].replaceAll("[^0-9]+","");
                Node node =new Node(Integer.parseInt(info[0]),Integer.parseInt(info[1]),Integer.parseInt(info[2]));
                heapArray.add(node);
            }

            reader.close();

        }catch (Exception e){
            System.out.println("Can't open heap file");
        }
        return heapArray;
    }

    //for the heap file hash map key -> id with information
    public static HashMap<Integer,Node> convertArrayToHashmap(ArrayList<Node> heapArray){
        HashMap<Integer,Node> heapHash=new HashMap<Integer,Node>();
        for(int i=0 ;i<heapArray.size();i++)
            heapHash.put(heapArray.get(i).getId(),heapArray.get(i));

        return heapHash;

    }

    // to link the nodes
    public static void linkNodes(String pointersPath , HashMap<Integer,Node> heapHash){
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pointersPath));
            while ((line=reader.readLine())!=null){
                String []info=line.split(",");
                info[0]=info[0].replaceAll("[^0-9]+","");
                //get the first node and make it's child the second node
                  heapHash.get(Integer.parseInt(info[0])).addChild(heapHash.get(Integer.parseInt(info[1])));


            }
        }catch (Exception e){
            System.out.println("Can't open pointers file");
        }
    }
    
    public static ArrayList<Node> sort(ArrayList<Node> heap){
        ArrayList<Node> h=new ArrayList<>();
        for (int i=0;i<heap.size();i++){
            int v=i;
            for(int j=i+1;j<heap.size()-1;j++){
                if(heap.get(i).getMemory_start()>heap.get(j).getMemory_start()){
                    v=j;
                }
            }
            Node temp = heap.get(i);
            heap.set(i,heap.get(v));
            heap.set(v,temp);
        }

        return heap;
    }
    
    public static void heapOut(ArrayList<Node> heap, String outPath){
        
        heap=sort(heap);
        try(FileWriter write =new FileWriter(new File(outPath)) ){
            for(Node node :heap){
                StringBuilder line =new StringBuilder();
                line.append(String.valueOf(node.getId()));
                line.append(",");
                line.append(String.valueOf(node.getMemory_start()));
                line.append(",");
                line.append((String.valueOf(node.getMemory_end())));
                line.append("\n");
                write.write(line.toString());


            }
        }catch (Exception e){
            System.out.println("error in creating file");
        }

    }
}
