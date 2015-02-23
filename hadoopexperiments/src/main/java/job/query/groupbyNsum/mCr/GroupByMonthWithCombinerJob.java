package job.query.groupbyNsum.mCr;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import components.combiner.DelayRecordMapCreationCombiner;
import components.mapper.DelayRecordMapCreationMapper;
import components.reducer.GroupByMonthWithMapInputReducer;

public class GroupByMonthWithCombinerJob extends Configured implements Tool{
    
    
  
 

public  int run(String[] allArgs) throws Exception {
	Job job = Job.getInstance(getConf());
    job.setJarByClass(GroupByMonthWithCombinerJob.class);
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
    
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(MapWritable.class);
    
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);

    job.setMapperClass(DelayRecordMapCreationMapper.class);
    job.setCombinerClass(DelayRecordMapCreationCombiner.class);
    job.setReducerClass(GroupByMonthWithMapInputReducer.class);
    job.setNumReduceTasks(1);

    
    String[] args = new GenericOptionsParser(getConf(), allArgs).getRemainingArgs();
    
    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));   
  
    boolean status = job.waitForCompletion(true);

    if (status) {
        return 0;
    } else {
        return 1;
    }
 }

 public static void main(String[] args) throws Exception {
    //Configuration conf = new Configuration();    
    ToolRunner.run(new GroupByMonthWithCombinerJob(), args);

 }

}