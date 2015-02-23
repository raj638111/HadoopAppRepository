package components.mapper;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import custom.utils.AirlineDataUtils;
import custom.utils.DelayConst;

public class DelayRecordMapCreationMapper extends Mapper<LongWritable, Text, Text, MapWritable> {

    
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
        if(!AirlineDataUtils.isHeader(value)){         
           String[] contents = value.toString().split(",");
           String month = AirlineDataUtils.getMonth(contents);           
           int arrivalDelay = AirlineDataUtils.parseMinutes(AirlineDataUtils.getArrivalDelay(contents),0);           
           int departureDelay = AirlineDataUtils.parseMinutes(AirlineDataUtils.getDepartureDelay(contents),0);           
           boolean isCancelled =  AirlineDataUtils.parseBoolean(AirlineDataUtils.getCancelled(contents),false);           
           boolean isDiverted =  AirlineDataUtils.parseBoolean(AirlineDataUtils.getDiverted(contents),false);           
           context.write(new Text(month), getMapWritable(DelayConst.RECORD,new IntWritable(1)));           
           if(arrivalDelay>0){
               context.write(new Text(month), getMapWritable(DelayConst.ARRIVAL_DELAY,new IntWritable(1)));               
           }
           else{
               context.write(new Text(month), getMapWritable(DelayConst.ARRIVAL_ON_TIME,new IntWritable(1)));               
           }
           if(departureDelay>0){
               context.write(new Text(month), getMapWritable(DelayConst.DEPARTURE_DELAY,new IntWritable(1)));
           }
           else{
               context.write(new Text(month), getMapWritable(DelayConst.DEPARTURE_ON_TIME,new IntWritable(1)));
           }
           if(isCancelled){
               context.write(new Text(month), getMapWritable(DelayConst.IS_CANCELLED,new IntWritable(1)));
           }      
           if(isDiverted){
               context.write(new Text(month), getMapWritable(DelayConst.IS_DIVERTED,new IntWritable(1)));
           }            
        }
    } 
    
    private MapWritable getMapWritable(IntWritable type,IntWritable value){
        MapWritable map = new MapWritable();
        map.put(DelayConst.TYPE, type);
        map.put(DelayConst.VALUE, value);
        return map;
    }
 }
