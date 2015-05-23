package components.combiner;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import custom.utils.DelayConst;

public class DelayRecordMapCreationCombiner extends Reducer<Text, MapWritable, Text, MapWritable> {
    public void reduce(Text key, Iterable<MapWritable> values, Context context) 
  throws IOException, InterruptedException {
        int totalRecords = 0;
        int arrivalOnTime = 0;
        int arrivalDelays = 0;
        int departureOnTime = 0;
        int departureDelays = 0;
        int cancellations = 0;
        int diversions = 0;
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
               arrivalDelays=arrivalDelays+value.get();
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
        context.write(key, getMapWritable(DelayConst.RECORD,new IntWritable(totalRecords)));     
        context.write(key, getMapWritable(DelayConst.ARRIVAL_ON_TIME,new IntWritable(arrivalOnTime)));     
        context.write(key, getMapWritable(DelayConst.ARRIVAL_DELAY,new IntWritable(arrivalDelays)));     
        context.write(key, getMapWritable(DelayConst.DEPARTURE_ON_TIME,new IntWritable(departureOnTime)));     
        context.write(key, getMapWritable(DelayConst.DEPARTURE_DELAY,new IntWritable(departureDelays)));     
        context.write(key, getMapWritable(DelayConst.IS_CANCELLED,new IntWritable(cancellations))); 
        context.write(key, getMapWritable(DelayConst.IS_DIVERTED,new IntWritable(diversions)));
  }
    
  private MapWritable getMapWritable(IntWritable type,IntWritable value){
      MapWritable map = new MapWritable();
      map.put(DelayConst.TYPE, type);
      map.put(DelayConst.VALUE, value);
      return map;
  }
}