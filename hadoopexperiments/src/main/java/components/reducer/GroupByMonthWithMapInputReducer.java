package components.reducer;

import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import custom.utils.DelayConst;

public class GroupByMonthWithMapInputReducer extends Reducer<Text, MapWritable, NullWritable, Text> {
    public void reduce(Text key, Iterable<MapWritable> values, Context context) 
  throws IOException, InterruptedException {
        double totalRecords = 0;
        double arrivalOnTime = 0;
        double arrivalDelays = 0;
        double departureOnTime = 0;
        double departureDelays = 0;
        double cancellations = 0;
        double diversions = 0;
        for(MapWritable v:values){   
           IntWritable type = (IntWritable)v.get(DelayConst.TYPE);
           IntWritable value = (IntWritable)v.get(DelayConst.VALUE);
           if(type.equals(DelayConst.RECORD)){
               totalRecords=totalRecords+value.get();
           }
           if(type.equals(DelayConst.ARRIVAL_ON_TIME)){
               arrivalOnTime=arrivalOnTime+value.get();
           }
           if(type.equals(DelayConst.ARRIVAL_DELAY)){
               arrivalDelays=arrivalOnTime+value.get();
           }
           if(type.equals(DelayConst.DEPARTURE_ON_TIME)){
               departureOnTime=departureOnTime+value.get();
           }            
           if(type.equals(DelayConst.DEPARTURE_DELAY)){
               departureDelays=departureDelays+value.get();
           }            
           if(type.equals(DelayConst.IS_CANCELLED)){
               cancellations=cancellations+value.get();
           }  
           if(type.equals(DelayConst.IS_DIVERTED)){
               diversions=diversions+value.get();
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
