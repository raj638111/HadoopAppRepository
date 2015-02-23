package custom.utils;

import org.apache.hadoop.io.IntWritable;

public class DelayConst {

	
	public static final IntWritable RECORD=new IntWritable(0);
    public static final IntWritable ARRIVAL_DELAY=new IntWritable(1);
    public static final IntWritable ARRIVAL_ON_TIME=new IntWritable(2);
    public static final IntWritable DEPARTURE_DELAY=new IntWritable(3);
    public static final IntWritable DEPARTURE_ON_TIME=new IntWritable(4);
    public static final IntWritable IS_CANCELLED=new IntWritable(5);
    public static final IntWritable IS_DIVERTED=new IntWritable(6);

}
