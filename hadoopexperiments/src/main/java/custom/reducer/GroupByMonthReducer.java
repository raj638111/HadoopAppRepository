package custom.reducer;

import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import custom.utils.DelayConst;

public class GroupByMonthReducer extends Reducer<Text, IntWritable, NullWritable, Text> {
	 
	 int inc = 0;
	 
	    
    public void reduce(Text key, Iterable<IntWritable> values, Context context) 
  throws IOException, InterruptedException {
        double totalRecords = 0;
        double arrivalOnTime = 0;
        double arrivalDelays = 0;
        double departureOnTime = 0;
        double departureDelays = 0;
        double cancellations = 0;
        double diversions = 0;
        for(IntWritable v:values){
       	 inc++;
       	 System.out.println("Key -> " + key + ", Index -> " + inc);
           if(v.equals(DelayConst.RECORD)){
               totalRecords++;
           }
           if(v.equals(DelayConst.ARRIVAL_ON_TIME)){
               arrivalOnTime++;
           }
           if(v.equals(DelayConst.ARRIVAL_DELAY)){
               arrivalDelays++;
           }
           if(v.equals(DelayConst.DEPARTURE_ON_TIME)){
               departureOnTime++;
           }            
           if(v.equals(DelayConst.DEPARTURE_DELAY)){
               departureDelays++;
           }            
           if(v.equals(DelayConst.IS_CANCELLED)){
               cancellations++;
           }  
           if(v.equals(DelayConst.IS_DIVERTED)){
               diversions++;
           } 
           
        }
        DecimalFormat df = new DecimalFormat( "0.0000" );
        StringBuilder output = new StringBuilder(key.toString());
        output.append(",").append(totalRecords);
       
        output.append(",").append(df.format(arrivalOnTime/totalRecords));
        output.append(",").append(df.format(arrivalDelays/totalRecords));
        output.append(",").append(df.format(departureOnTime/totalRecords));
        output.append(",").append(df.format(departureDelays/totalRecords));
        output.append(",").append(df.format(cancellations/totalRecords));
        output.append(",").append(df.format(diversions/totalRecords));
        context.write(NullWritable.get(), new Text(output.toString()));
  }
}
