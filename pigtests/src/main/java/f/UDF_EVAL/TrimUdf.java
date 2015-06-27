package f.UDF_EVAL;
import java.io.IOException;

import org.apache.pig.PrimitiveEvalFunc;

/*
 * A UDF Function to trime a given String and return a String #452
 	REGISTER target/pigtests-0.0.1-SNAPSHOT.jar;
	records = 	LOAD 'src/main/resources/input/pipe_sample.txt' USING PigStorage('|')
				AS (year:chararray, temperature:int, quality:int);
	trimmed = FOREACH records GENERATE(	f.EVAL_UDF.TrimUdf(year), temperature, quality);
 */
public class TrimUdf extends PrimitiveEvalFunc<String, String> {

	@Override
	public String exec(String input) {
		return input.trim();
	}
}
