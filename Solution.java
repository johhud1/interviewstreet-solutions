import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Point;
import java.awt.Rectangle;

public class Solution {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String sParams;
        try {
            sParams = in.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        int testcases = Integer.parseInt(sParams);
        for(int i=0; i<testcases; i++){
            try {
                runTestCase(in);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private static void runTestCase(BufferedReader in) throws IOException{
        String testParameters = in.readLine();
        //System.out.println("test parameters are: "+testParameters);
        String[] tParams = testParameters.split("\\s");
        int numRects = Integer.parseInt(tParams[0]);
        int numPoints = Integer.parseInt(tParams[1]);
        //System.out.println("numRects = "+numRects+" numPoints = "+numPoints);
        ArrayList<Rectangle> myRects = new ArrayList<Rectangle>();
        for(int i=0; i<numRects; i++){
            String[] rectBounds = in.readLine().split("\\s");
            int height = Integer.parseInt(rectBounds[3]) - Integer.parseInt(rectBounds[1]);
            int width = Integer.parseInt(rectBounds[2]) - Integer.parseInt(rectBounds[0]);
            int x1, y1;
            try{
                x1 = Integer.parseInt(rectBounds[0]);
                y1 = Integer.parseInt(rectBounds[1]);
            }
            catch(NumberFormatException e){
                i--;
                continue;
            }
            myRects.add(new Rectangle(x1, y1, width, height));
        }
        //System.out.println("myRects: "+myRects);
        for(int i=0; i<numPoints; i++){
            int numProts=0;
            Iterator<Rectangle> it = myRects.listIterator();
            String[] point_cords = in.readLine().split("\\s");
            Point pnt;
            try{
                pnt = new Point(Integer.parseInt(point_cords[0]), Integer.parseInt(point_cords[1]));
            }
            catch (NumberFormatException e){
                i--;
                continue;
            }
            while(it.hasNext()){
                Rectangle testRect = it.next();
                //System.out.println("testing pnt: "+pnt+" and Rect: "+testRect);
                if(contains(testRect, pnt)){
                    //System.out.println("found rectangle containing point!!");
                    numProts++;
                }
            }
            System.out.println(numProts);
        }
    }

    private static boolean contains(Rectangle rect, Point pnt){
        int x = pnt.x;
        int y = pnt.y;
        if( (x >= rect.getMinX()) && (x<= rect.getMaxX()) && (y>= rect.getMinY()) && (y<= rect.getMaxY()) ){
            return true;
        }
        else return false;
    }

}
