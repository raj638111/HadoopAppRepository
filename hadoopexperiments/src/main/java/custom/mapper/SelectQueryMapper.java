package custom.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import custom.utils.AirlineDataUtils;

public class SelectQueryMapper extends
		Mapper<LongWritable, Text, NullWritable, Text> {

	String cn = this.getClass().getSimpleName();
	
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		if (!AirlineDataUtils.isHeader(value)) {
			
			System.out.println(cn + " : Key -> " + key.get() + ", Value -> "
					+ value.toString());
			
			StringBuilder output = AirlineDataUtils.mergeStringArray(
					AirlineDataUtils.getSelectResultsPerRow(value), ",");
			context.write(NullWritable.get(), new Text(output.toString()));
		} else {
			System.out.println(cn + " : THIS IS HEADER");
		}

	}
}
