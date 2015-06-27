package e.UDF_FILTER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pig.FilterFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

/*
	REGISTER target/pigtests-0.0.1-SNAPSHOT.jar;
	
	records = 	LOAD 'src/main/resources/input/tab_sample.txt'
				AS (year:chararray, temperature:int, quality:int);
	filtered_records = FILTER records BY temperature != 9999 AND
					e.FILTER_UDF.IsGoodQuality(quality);
	
	//Note : 	Package can also be added using Macro or import option 
	//			-Dudf.import.list=e.FILTER_UDF
	//			More here...#451
 */
public class IsGoodQuality extends FilterFunc {

	@Override
	public Boolean exec(Tuple tuple) throws IOException {
		if (tuple == null || tuple.size() == 0) {
			return false;
		}
		try {
			Object object = tuple.get(0);
			if (object == null) {
				return false;
			}
			int i = (Integer) object;
			return i == 0 || i == 1 || i == 4 || i == 5 || i == 9;
		} catch (ExecException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Convert the first field available in 'tuple' argument to Integer
	 * If not possible set that value to 'null' so that exec will return 
	 * 'false' #451
	 * 
	 *  This method is useful when schema type is not specified for a Relation
	 *  
	   		REGISTER target/pigtests-0.0.1-SNAPSHOT.jar;
	
			records = 	LOAD 'src/main/resources/input/offending_sample.txt'
						AS (year, temperature, quality);
			filtered_records = FILTER records BY temperature != 9999 AND
					e.FILTER_UDF.IsGoodQuality(quality);

	 */
	@Override
	public List<FuncSpec> getArgToFuncMapping() throws FrontendException {

		List<FuncSpec> funcSpecs = new ArrayList<FuncSpec>();

		funcSpecs.add(	new FuncSpec(this.getClass().getName(), 
						new Schema(new Schema.FieldSchema(null, DataType.INTEGER))));

		return funcSpecs;

	}

}
