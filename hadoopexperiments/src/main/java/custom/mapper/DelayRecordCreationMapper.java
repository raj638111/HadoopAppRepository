package custom.mapper;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import custom.utils.AirlineDataUtils;
import custom.utils.DelayConst;

public class DelayRecordCreationMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    
	 private int inc = 0;
    
   public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
       
       if(!AirlineDataUtils.isHeader(value)){         
          String[] contents = value.toString().split(",");
          String month = AirlineDataUtils.getMonth(contents);           
          int arrivalDelay = AirlineDataUtils.parseMinutes(AirlineDataUtils.getArrivalDelay(contents),0);           
          int departureDelay = AirlineDataUtils.parseMinutes(AirlineDataUtils.getDepartureDelay(contents),0);           
          boolean isCancelled =  AirlineDataUtils.parseBoolean(AirlineDataUtils.getCancelled(contents),false);           
          boolean isDiverted =  AirlineDataUtils.parseBoolean(AirlineDataUtils.getDiverted(contents),false);
          inc++;
          System.out.println("Mapper : Record : [" + inc + "]" + ", Month -> " + month);
          context.write(new Text(month), DelayConst.RECORD);           
          if(arrivalDelay>0){
              context.write(new Text(month), DelayConst.ARRIVAL_DELAY);
          }
          else{
              context.write(new Text(month), DelayConst.ARRIVAL_ON_TIME);
          }
          if(departureDelay>0){
              context.write(new Text(month), DelayConst.DEPARTURE_DELAY);
          }
          else{
              context.write(new Text(month), DelayConst.DEPARTURE_ON_TIME);
          }
          if(isCancelled){
              context.write(new Text(month), DelayConst.IS_CANCELLED);
          }      
          if(isDiverted){
              context.write(new Text(month), DelayConst.IS_DIVERTED);
          }            
       }
   } 
}
