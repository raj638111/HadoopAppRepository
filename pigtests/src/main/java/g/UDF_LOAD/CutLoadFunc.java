//cc CutLoadFunc A LoadFunc UDF to load tuple fields as column ranges
package g.UDF_LOAD;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.pig.LoadFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;

/*
 * Create 3 fields based on column ranges in the given input file
 * (the file that we need to load) #453
 * 
 * Breadcrumb : @override getLoadCaster() 
 * 				Can be used to provide type(Schema) for the fields #456
 * 
 	REGISTER target/pigtests-0.0.1-SNAPSHOT.jar;
 	records = LOAD 'src/main/resources/input/cut_load.txt'	
			USING g.UDF_LOAD.CutLoadFunc('1-4,5-6,7-7')
			AS (year:int, temperature:int, quality:int);
			   
 */
public class CutLoadFunc extends LoadFunc {

	private static final Log LOG = LogFactory.getLog(CutLoadFunc.class);

	private final List<Range> ranges;
	private final TupleFactory tupleFactory = TupleFactory.getInstance();
	private RecordReader reader;

	public CutLoadFunc(String cutPattern) {
		LOG.error("Hello Constructor.....");
		ranges = Range.parse(cutPattern);
	}
	
	//Set the Location of the Input File
	@Override
	public void setLocation(String location, Job job) throws IOException {
		FileInputFormat.setInputPaths(job, location);
	}

	//Set the Input format to load the file
	@Override
	public InputFormat getInputFormat() {
		return new TextInputFormat();
	}

	//Store the Record reader as a Reference for later use
	@Override
	public void prepareToRead(RecordReader reader, PigSplit split) {
		this.reader = reader;
	}

	//Called for each record in the file
	@Override
	public Tuple getNext() throws IOException {
		try {
			if (!reader.nextKeyValue()) {
				return null;
			}
			Text value = (Text) reader.getCurrentValue();
			//Convert the record into a String
			String line = value.toString(); 
			//Create a Tuple with size() fields
			Tuple tuple = tupleFactory.newTuple(ranges.size());
			
			for (int i = 0; i < ranges.size(); i++) {
				Range range = ranges.get(i);
				if (range.getEnd() > line.length()) {
					LOG.warn(String.format(
							"Range end (%s) is longer than line length (%s)",
							range.getEnd(), line.length()));
					continue;
				}
				//Set each field in the Tuple
				tuple.set(i, new DataByteArray(range.getSubstring(line)));
			}
			return tuple;
		} catch (InterruptedException e) {
			throw new ExecException(e);
		}
	}
}
