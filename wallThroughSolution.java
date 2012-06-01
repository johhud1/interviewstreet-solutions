/*
 Enter your code here. Read input from STDIN. Print output to STDOUT
 Your class should be named Solution
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class wallThroughSolution{

    public static class IntervalPoint implements Comparable{
        public Boolean mIsEnd;
        public IntervalPoint otherPoint;
        public int mPos;
        public Boolean isValid;

        public IntervalPoint(int pos, Boolean isEnd){
            mPos = pos;
            mIsEnd = isEnd;
            isValid = true;
        }
        public int compareTo(Object a){
            int comparison = ((Integer)mPos).compareTo( ((IntervalPoint)a).mPos );
            if(comparison ==0){
                //System.out.println("found overlap at pt "+((IntervalPoint)a).mPos);
                if(mIsEnd == ((IntervalPoint)a).mIsEnd){
                    //System.out.println("both begginning or end, they are equal");
                    return 0;
                }
                if( (mIsEnd == true) && ( ((IntervalPoint)a).mIsEnd == false)){
                    //System.out.println("comparing this(end) to beginning, this is bigger so return 1");
                    return 1;
                }
                if((mIsEnd == false) && ( ((IntervalPoint)a).mIsEnd == true)){
                    //System.out.println("comparing this(beginning) to end, beginning is less so return -1");
                    return -1;
                }
            }
            return comparison;
        }
        public boolean equal(IntervalPoint obj){
            return ((Integer)mPos).equals(obj.mPos) && !(mIsEnd ^ obj.mIsEnd);
        }
    }

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String numWalls="0";
        try {
            numWalls = in.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //System.out.println("error reading number of walls");
            e.printStackTrace();
        }
        int i_numWalls = Integer.parseInt(numWalls);
        PriorityQueue<IntervalPoint> intervals = new PriorityQueue<IntervalPoint>();
        for(int i=0; i < i_numWalls; i++){
            //System.out.println("reading wall num:"+i);
            String[] intervalBounds = null;
            try {
                 intervalBounds = in.readLine().split("\\s");
                 if(intervalBounds.length<2){
                     i--;
                     //System.out.println("uhoh, reading line, had less than 2 vals ????");
                     continue;
                 }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int startpos = Integer.parseInt(intervalBounds[0]);
            int endpos = Integer.parseInt(intervalBounds[1]);
            IntervalPoint sp = new IntervalPoint(startpos, false);
            IntervalPoint ep = new IntervalPoint(endpos, true);
            sp.otherPoint = ep;
            ep.otherPoint = sp;
            intervals.add(sp);
            intervals.add(ep);
        }
        if(i_numWalls != intervals.size()/2){
            //System.out.println("uhoh, numwalls ("+numWalls+") doesn't equal myQ.size() "+intervals.size()/2);
        }
        int canHit=0;
        int numshots=0;
        PriorityQueue<IntervalPoint> tempInts = new PriorityQueue<IntervalPoint>();
        //System.out.println("intervals.size() = "+intervals.size());
        while(intervals.size()>0){
            IntervalPoint np = intervals.poll();
            if(!np.mIsEnd){
                //System.out.println("beggining intervalPoint at "+np.mPos+" isEnd="+np.mIsEnd);
                canHit++;
                tempInts.add(np);
            }
            else if((np.mIsEnd) && np.isValid){
                //System.out.println("found valid end of interval at np.pos= "+np.mPos+" adding 1 to numshots");
                canHit = 0;
                numshots++;
                while(tempInts.size()>0){
                    IntervalPoint tempint = tempInts.poll();
                    //System.out.println("invalidating point at: "+tempint.otherPoint.mPos);
                    tempint.otherPoint.isValid=false;
                }
                continue;
            }
            if(!np.isValid){
                //System.out.println("np at "+np.mPos+" is invalid");
            }
        //System.out.println("intervals.size() = "+intervals.size());
        }
        System.out.println(numshots);
    }
}
