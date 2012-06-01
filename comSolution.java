import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class comSolution {
    public static void main(String[] args){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int numBlocks=0;
        int[] blockWeights=null;
        int[][] adjMat=null;
        try {
            numBlocks = Integer.parseInt(in.readLine().split(" ")[0]);
            //System.out.println("numBlock "+numBlocks);
            adjMat = new int[numBlocks][numBlocks];
            blockWeights = new int[numBlocks];
            String[] weights = in.readLine().split(" ");
            for(int i=0; i<numBlocks; i++){
                blockWeights[i] = Integer.parseInt(weights[i]);
            }
            for(int i=0; i<numBlocks; i++){
                String adjString = in.readLine();
                int k=0;
                for(char a:adjString.toCharArray()){
                    if(a=='N'){
                        adjMat[i][k] = 0;
                    }else{
                        adjMat[i][k]=1;
                    }
                    //System.out.print(adjMat[i][k]);
                    k++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int[][] blockAdjBefore=adjMat;
        int[][] blockAdjAfter=adjMat;
        do{
            blockAdjBefore = blockAdjAfter;
            blockAdjAfter = matMult(blockAdjBefore, blockAdjAfter);
        } while(!equalMats(blockAdjBefore, blockAdjAfter));

        blockWeights = sortWeights(blockWeights, blockAdjAfter);
        for(int a:blockWeights){
            System.out.print(a+" ");
        }
    }

    private static boolean equalMats(int[][] a, int[][]b){
        if(a.length!=b.length || a[0].length!=b[0].length){
            System.out.println("uhoh, comparing mismatching (length) matrices!!");
            return false;
        }
        for(int i=0; i<a.length; i++){
            for(int k=0; k<a[i].length; k++){
                if( (a[i][k]!=0 && b[i][k]==0) ||(a[i][k]==0 && b[i][k]!=0)){
                    //System.out.println("found exponentiated matrices still unequal");
                    //System.out.println("a["+i+"]["+k+"]="+a[i][k]+" b[i][k]="+b[i][k]);
                    return false;
                }
            }
        }
        return true;
    }
    private static int[] sortWeights(int[] blockWeights, int[][] blockAdjAfter){
        for(int i=0; i<blockAdjAfter.length; i++){
            for(int k=0; k<blockAdjAfter[i].length; k++){
                if(blockAdjAfter[i][k]!=0 && (blockWeights[i]>blockWeights[k]) && i<k){
                    int temp = blockWeights[i];
                    blockWeights[i]=blockWeights[k];
                    blockWeights[k] = temp;
                }
            }
        }
        return blockWeights;
    }
    private static int[][] matMult(int[][] a, int[][] b){
        int[][] results = new int[a.length][a[0].length];
        for(int i=0; i<a.length; i++){
            for(int k=0; k<a[i].length; k++){
                results[i][k] = dot(a[i], b[k]);
            }
        }
        return results;
    }
    private static int dot(int[] row, int[] col){
        int res=0;
        for(int i=0; i<row.length; i++){
            for(int k=0; k<col.length; k++){
                res = res+row[i]*col[k];
            }
        }
        return res;
    }
}
