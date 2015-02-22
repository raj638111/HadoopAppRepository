package job.query.where.m;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import custom.mapper.WhereQueryMapper;

//Select field1, field2, ... from <Input file> where DelayInArrival > 10min (or) DelayInDeparture > 10 min
//hadoop jar <*.jar> <Package.classname> -D map.where.delay=10 /input /output


public class WhereQueryJob extends Configured implements Tool {

	String cn = this.getClass().getSimpleName();
	
    public int run(String[] allArgs) throws Exception {
        Job job = Job.getInstance(getConf());
        job.setJarByClass(WhereQueryJob.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(WhereQueryMapper.class);
        job.setNumReduceTasks(0);

        String[] args = new GenericOptionsParser(getConf(), allArgs)
                .getRemainingArgs();
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
        int res = ToolRunner.run(new WhereQueryJob(), args);
    }

}