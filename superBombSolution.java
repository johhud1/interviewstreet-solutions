import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class superBombSolution {


    public static void main(String[] args){

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] gridHeightWidth;
        try {
            gridHeightWidth = in.readLine().split(" ");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        int height = Integer.parseInt(gridHeightWidth[0]);
        int width = Integer.parseInt(gridHeightWidth[1]);
        int[][] bombMatrix = new int[height][width];
        for(int i=0; i<height; i++){
            String[] rowString=null;
            try {
                rowString = in.readLine().split(" ");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(rowString.length!=width){
                System.out.println("UHOH, error mismatch between row string length and width params!");
            }
            for(int k=0; k<width; k++){
                bombMatrix[i][k] = Integer.parseInt(rowString[k]);
            }
        }
        int result = recursiveSolution(bombMatrix);
        System.out.println(result);
    }

    public static int recursiveSolution(int[][] bombMatrix){
        int max=0;
        for(int i=0; i<bombMatrix.length;i++){
            for(int k=0; k<bombMatrix[i].length;k++){
                if(bombMatrix[i][k]==1){
                    int[][] rBombMatrix = new int[bombMatrix.length][bombMatrix[i].length];
                    System.arraycopy(bombMatrix, 0, rBombMatrix, 0, bombMatrix.length);
                    markRowColumnZero(rBombMatrix, i, k);
                    int temp = 1+recursiveSolution(rBombMatrix);
                    if(temp>max){
                        max = temp;
                    }
                }
            }
        }
        return max;
    }

    private static void markRowColumnZero(int[][] bombMatrix, int row, int col){
        for(int i=0; i<bombMatrix.length; i++){
            bombMatrix[i][col]=0;
        }
        for(int k=0; k<bombMatrix[row].length; k++){
            bombMatrix[row][k] = 0;
        }
    }
}
