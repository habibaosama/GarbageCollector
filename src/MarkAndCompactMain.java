public class MarkAndCompactMain {
    public static void main(String[] args){
        String[] arg = new String[4];
        arg[0] = System.getProperty("user.dir")+"\\src\\heap.csv";
        arg[1] =  System.getProperty("user.dir")+"\\src\\roots.txt";
        arg[2] =System.getProperty("user.dir")+"\\src\\pointers.csv";;
        arg[3] = System.getProperty("user.dir")+"\\src\\new-heap.csv";
        MarkAndCompact mark =new MarkAndCompact(arg[0],arg[1],arg[2]);
        Files.heapOut(mark.markCompact() , arg[3]);
    }
}
