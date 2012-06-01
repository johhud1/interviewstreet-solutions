import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;


public class MatchingSolution {
    public static class Tuple implements Comparable<Tuple>{
        public final Integer x;
        public final Integer y;
        public Tuple(Integer x, Integer y) {
          this.x = x;
          this.y = y;
        }
        @Override
        public String toString(){
            return x.toString()+" "+y.toString();
        }

        @Override
        public boolean equals(Object o){
            if(this.x == ((Tuple)o).x && this.y == ((Tuple)o).y){
                return true;
            }
            else return false;
        }
        @Override
        public int compareTo(Tuple o) {
            if(this.x == o.x && this.y == o.y){
                return 0;
            }
            if(this.y < o.y){
                return -1;
            }else{
                return 1;
            }
        }
      }
    public static void main(String[] args){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] inputStr;
        try {
            inputStr = in.readLine().split(" ");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        int n=0;
        if(inputStr.length!=1) System.out.println("uhoh, inputString[] length not 1 !!!");
        else n = Integer.parseInt(inputStr[0]);


        int netM=0;
        HashMap<String, Long> solutionsForN = new HashMap<String, Long>();
        Long numPossibleSeqs= recursiveSolve(solutionsForN, netM, 2*n);
        System.out.println(numPossibleSeqs.longValue() %  1000000007);
    }

    public static Long recursiveSolve(HashMap<String, Long> solutionsForN, int netM, int n){
        //System.out.println("recursiveSolve(netM="+netM+" n="+n+")");
        Long sequences=(long) 0;
        Long tempSol = solutionsForN.get(netM+""+n);
        if(tempSol!=null){
            //System.out.println("using stored solution!!");
            sequences = tempSol.longValue();
            return sequences;
        }
        if(netM==0){
            netM++;
            sequences = recursiveSolve(solutionsForN, netM, n-1);
            }
        else if(netM==n){
            return (long) 1;
        }
        else{
            sequences = (sequences + recursiveSolve(solutionsForN, netM+1, n-1)%  1000000007);
            sequences = (sequences + recursiveSolve(solutionsForN, netM-1, n-1)%  1000000007);
        }
//        Set<Tuple> keySet = solutionsForN.keySet();
//        for(Tuple key:keySet){
//            System.out.println(key);
//        }
        solutionsForN.put(netM+""+n,(sequences %  1000000007));
        //System.out.println("added numsequence="+sequences);

        return (sequences %  1000000007);
    }

}
