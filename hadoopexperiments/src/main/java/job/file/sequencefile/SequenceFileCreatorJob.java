package job.file.sequencefile;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import custom.utils.AirlineDataUtils;
import custom.writable.DelaysValue;
import custom.writable.MonthAndDayOfWeekKey;

//src/main/resources/input/airlinedataset/sampledata
//src/main/resources/output
//hadoop fs -text file:///home/cloudera/workspace/gitworkspace/HadoopAppRepository/hadoopexperiments/src/main/resources/output/part-m-00000
//HADOOP_CLASSPATH

public class SequenceFileCreatorJob extends Configured implements Tool{

	
	public static class XMLToSequenceFileConversionMapper extends
			Mapper<LongWritable, Text, LongWritable, DelaysValue> {

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			System.out.println("map() : Key -> " + key + ", Value -> " + value );
			
			DelaysValue dv = new DelaysValue();
			dv = AirlineDataUtils.parseDelaysValue(value.toString());
			System.out.println("Year -> " + dv.year);
			
			context.write(key, dv);

		}
	}
	
	@Override
	public int run(String[] allArgs) throws Exception {
		
        Job job = Job.getInstance(getConf());
        job.setJarByClass(SequenceFileCreatorJob.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
    
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(DelaysValue.class);

       
        job.setMapperClass(XMLToSequenceFileConversionMapper.class);       
        job.setNumReduceTasks(0);

        String[] args = new GenericOptionsParser(getConf(), allArgs)
                .getRemainingArgs();
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        
       
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
       
        //SequenceFileOutputFormat.setCompressOutput(job, true);
        //SequenceFileOutputFormat.setOutputCompressorClass(job, SnappyCodec.class);


        //SequenceFileOutputFormat.setOutputCompressionType(job,CompressionType.BLOCK);
        //SequenceFileOutputFormat.setOutputCompressionType(job,CompressionType.RECORD);

        job.waitForCompletion(true);
		
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
        //Configuration conf = new Configuration();
        ToolRunner.run(new SequenceFileCreatorJob(), args);
    }

}


