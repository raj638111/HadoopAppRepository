package com.demo.helloworld;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class HelloMapReduceNew extends Configured implements Tool {

	public static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	    private final static IntWritable one = new IntWritable(1);

	    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String w = value.toString();
			context.write(new Text(w), one);
	    } }

	 public static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	        public void reduce(Text key, Iterable<IntWritable> values, Context context) 
	      throws IOException, InterruptedException {
	        if(StringUtils.isAlphanumeric(key.toString()) && !StringUtils.isAlphaSpace(key.toString()) ){
	        	int sum = 0;
	            for (IntWritable val : values) {
	                sum += val.get();
	            } 
	            context.write(key, new IntWritable(sum));
	        }
	    }
	 }
	
	
	
	public int run(String[] allArgs) throws Exception {
		Job job = Job.getInstance(getConf());
		job.setJarByClass(HelloMapReduceNew.class);
		
	    job.setOutputKeyClass(Text.class);			//?? : 	What if I do not set the out key & value class
	    											//		This is a must and conveyed as Redundant in the book
	    job.setOutputValueClass(IntWritable.class);

	    job.setMapperClass(MyMapper.class);
	    job.setReducerClass(MyReducer.class); 		//By default the No of Reducers is '1'

	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);
	    
	    String[] args = new GenericOptionsParser(getConf(), allArgs).getRemainingArgs();
	    FileInputFormat.setInputPaths(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    job.submit();

		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ToolRunner.run(new HelloMapReduceNew(), args);
	}

}
