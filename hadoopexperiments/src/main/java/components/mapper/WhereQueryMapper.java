package components.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import custom.utils.AirlineDataUtils;

public class WhereQueryMapper extends
		Mapper<LongWritable, Text, NullWritable, Text> {

	String cn = this.getClass().getSimpleName();
	
	private int delayInMinutes = 0;

	//Invoked once the Mapper starts. There is also another method
	//cleanup() that runs at end of each job
	@Override
	public void setup(Context context) {
		this.delayInMinutes = context.getConfiguration().getInt(
				"map.where.delay", 1);
	}

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		if (AirlineDataUtils.isHeader(value)) {
			return;// Only process non header rows
		}
		String[] arr = AirlineDataUtils.getSelectResultsPerRow(value);
		String depDel = arr[8];
		String arrDel = arr[9];
		int iDepDel = AirlineDataUtils.parseMinutes(depDel, 0);
		int iArrDel = AirlineDataUtils.parseMinutes(arrDel, 0);
		StringBuilder out = AirlineDataUtils.mergeStringArray(arr, ",");
		
		//This Check Corresponds to WHERE Query
		if (iDepDel >= this.delayInMinutes && iArrDel >= this.delayInMinutes) {
			System.out.println(cn + " : Both Departure & Arrival Delay");
			out.append(",").append("B");
			context.write(NullWritable.get(), new Text(out.toString()));
		} else if (iDepDel >= this.delayInMinutes) {
			System.out.println(cn + " : Departure Delay");
			out.append(",").append("O");
			context.write(NullWritable.get(), new Text(out.toString()));
		} else if (iArrDel >= this.delayInMinutes) {
			System.out.println(cn + " : Arrival Delay");
			out.append(",").append("D");
			context.write(NullWritable.get(), new Text(out.toString()));
		}

	}
	
	
}
