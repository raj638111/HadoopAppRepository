package job.sort.totalordersort;

import job.sort.totalordersort.SortAscMonthDescWeekMRJob.SortAscMonthDescWeekMapper;
import job.sort.totalordersort.SortAscMonthDescWeekMRJob.SortAscMonthDescWeekReducer;
import junit.framework.TestCase;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.junit.Before;
import org.junit.Test;

public class MRUnitTest extends TestCase{

	private Mapper mapper;
	private Reducer reducer;
	private MapReduceDriver driver;
	@Before
	public void setup() {
		mapper = new SortAscMonthDescWeekMapper();
		reducer = new SortAscMonthDescWeekReducer();
		driver = new MapReduceDriver(mapper, reducer);
	}
	
	@Test
	public void testMapperReducer() {
		driver.withInput(new LongWritable(1), 
				new Text("1987,12,30,3,1438,1315,1715,1612,CO,757,NA,157,177,NA,63,83,EWR,PBI,1024,NA,NA,0,NA,0,NA,NA,NA,NA,NA"));
	}
	
}
