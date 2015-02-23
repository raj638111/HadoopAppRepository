package job.query.groupbyNsum.mR;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import custom.mapper.DelayRecordCreationMapper;
import custom.reducer.GroupByMonthReducer;


//SELECT month, count(*), Proportion(arrivalOnTime), Proportion(arrivalOnDelay), .... from <Input File> GROUP BY Month

public class GroupByMonthJob extends Configured implements Tool{
	
 
public  int run(String[] allArgs) throws Exception {
	
	Job job = Job.getInstance(getConf());
    job.setJarByClass(GroupByMonthJob.class);
    
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
    
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    
    job.setOutputKeyClass(NullWritable.class);
    job.setOutputValueClass(Text.class);
    
    job.setMapperClass(DelayRecordCreationMapper.class);
    job.setReducerClass(GroupByMonthReducer.class);
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
    ToolRunner.run(new GroupByMonthJob(), args);

 }

}