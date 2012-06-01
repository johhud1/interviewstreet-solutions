import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class quoraClassifierSolution {
    static class DataPt{
        double[] mVector;
        String mId;
        int mRating;
        public DataPt(String id, double[] vect, int rating){
            mVector = vect;
            mId = id;
            mRating = rating;
        }
        public DataPt(String[] inputline, boolean isEval, int numFeats){
            mId = inputline[0];
            int start=0;
            if(isEval){
                mRating = 0;
                start=1;
            }
            else{
                start=2;
                if(inputline[1].charAt(0) == '+') mRating = Integer.parseInt(inputline[1].substring(1));
                else mRating = Integer.parseInt(inputline[1]);
            }
            mVector = new double[numFeats];
            for(int i=start; i<inputline.length; i++){
                double val = Double.parseDouble(inputline[i].split(":")[1]);
                mVector[i-start] = val;
            }
        }
        public String toString(){
            String rate;
            if(mRating ==1){
                rate = "+1";
            }
            else rate = "-1";
            return mId+" "+rate;
            //return "ID:"+mId+" mRating:"+mRating;//+" mVector:"+Arrays.toString(mVector);
        }
    }

    public static void main(String[] args) {
        /*try {
            System.setIn(new FileInputStream("/home/john/workspace/quora/input00.txt"));
        } catch (FileNotFoundException e1) {
            System.out.println("file Not found");
            e1.printStackTrace();
        }*/
        BufferedReader inputStr = new BufferedReader(new InputStreamReader(System.in));
        String mArgs = null;

        //parse training data
        try {
            mArgs = inputStr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] arg_array = mArgs.split(" ");
        int num_training_data = Integer.parseInt(arg_array[0]);
        int num_feats = Integer.parseInt(arg_array[1]);
        //System.out.println("training data; Num_training_data: "+num_training_data+" num_features="+num_feats);
        ArrayList<DataPt> training_data_points_P1 = new ArrayList<DataPt>();
        ArrayList<DataPt> training_data_points_M1 = new ArrayList<DataPt>();
        int num_plus1_pts=0;
        int num_minus1_pts=0;

        for(int i=0; i<num_training_data; i++){
            String inputline[];
            try {
                inputline = inputStr.readLine().split(" ");
                DataPt newDP = new DataPt(inputline, false, num_feats);
                if(newDP.mRating==1){
                    training_data_points_P1.add(newDP);
                    num_plus1_pts++;
                } else if(newDP.mRating==-1){
                    training_data_points_M1.add(newDP);
                    num_minus1_pts++;
                }
            } catch (IOException e) {
                System.out.println("ioexeception parsing data points");
                e.printStackTrace();
            }
            //System.out.println(training_data_points[i].toString());
        }
        //System.out.println("num_plus_1("+num_plus1_pts+") + num_minus_1("+num_minus1_pts+") = "+(int)(num_plus1_pts+num_minus1_pts)+" and total_pts ="+num_training_data);

        //compute avg position of group +1 from training set
        double[] group_p1_avg = new double[num_feats];
        DataPt[] group_p1_dps = new DataPt[training_data_points_P1.size()];
        group_p1_dps = training_data_points_P1.toArray(group_p1_dps);
        group_p1_avg = compute_avg(group_p1_dps);

        //compute avg position of group -1 from training set
        double[] group_m1_avg = new double[num_feats];
        DataPt[] group_m1_dps = new DataPt[training_data_points_M1.size()];
        group_m1_dps = training_data_points_M1.toArray(group_m1_dps);
        group_m1_avg = compute_avg(group_m1_dps);

        //System.out.println("Avg +1 position vector is: "+Arrays.toString(group_p1_avg));
        //System.out.println("Avg -1 position vector is: "+Arrays.toString(group_m1_avg));


        //parsing evaluation set
        int num_eval_data=0;
        try {
            num_eval_data = Integer.parseInt(inputStr.readLine());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("eval_data_points "+num_eval_data);
        DataPt[] eval_data_points = new DataPt[num_training_data];
        for(int i=0; i<num_eval_data; i++){
            String inputline[];
            try {
                inputline = inputStr.readLine().split(" ");
                eval_data_points[i] = new DataPt(inputline, true, num_feats);
            } catch (IOException e) {
                System.out.println("ioexeception parsing data points");
                e.printStackTrace();
            }
            //comparing distance too avg +1 and -1 vector
            double dist_m1 = distance(eval_data_points[i].mVector, group_m1_avg);
            double dist_p1 = distance(eval_data_points[i].mVector, group_p1_avg);

            int group=0;
            //System.out.println("dist_m1="+dist_m1);
            //System.out.println("dist_p1="+dist_p1);
            if(dist_m1>dist_p1){
                group = 1;
            }else group =-1;
            eval_data_points[i].mRating=group;
            System.out.println(eval_data_points[i].toString());
        }
    }

    public static double[] v_sum(double[] a, double[] b){
        if(a.length != b.length){
            System.out.println("ERROR adding 'vectors' of unequal length. a.length="+a.length+" b.length="+b.length);
            return null;
        }
        double[] sum = new double[a.length];
        for(int i = 0; i<a.length; i++){
            sum[i] = a[i]+b[i];
        }
        return sum;
    }
    public static double[] v_division(double[] v, int d){
        for(int i=0; i<v.length; i++){
            v[i] = v[i]/d;
        }
        return v;
    }
    public static double[] compute_avg(DataPt[] dataPts){
        if(dataPts.length<2){
            if(dataPts.length<1){
                System.out.println("ERROR: tried to compute_avg of 0 or fewer vectors");
                return null;
            }
            return dataPts[1].mVector;
        }
        double[] result = new double[dataPts[0].mVector.length];
        for(int i=0; i<dataPts.length; i++){
            result = v_sum(result, dataPts[i].mVector);
        }
        return v_division(result, dataPts.length);
    }
    public static double distance(double[] a, double[] b){
        if(a.length!=b.length){
            System.out.println("ERROR mismatched vector lengths; a.length="+a.length+" b.length="+b.length);
            return (Double) null;
        }
        double ret=0;
        for(int i=0; i<a.length; i++){
            ret = ret + Math.pow(a[i]-b[i], 2);
        }
        return Math.sqrt(ret);
    }
}
