package com.demo.filetests;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 
 * A SequenceFile is Binary file that stores Key/Value pairs
 *
 */
public class XmlToSequenceFileCoversionJob extends Configured implements Tool {
	
	public int run(String[] args) throws IOException  {
		
		//Create a new Job
		Job job = Job.getInstance(getConf());
		job.setJarByClass(XmlToSequenceFileCoversionJob.class);
		
		//Set Job configuration
				
		
		return 0;
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ToolRunner.run(new XmlToSequenceFileCoversionJob(), args);
	}

}
