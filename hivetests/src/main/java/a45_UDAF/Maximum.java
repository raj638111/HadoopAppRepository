package a45_UDAF;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.io.IntWritable;

public class Maximum extends UDAF {
	
	/*
	 * Create a Nested Static Class & implement UDFEvaluator
	 * http://stackoverflow.com/questions/7486012/static-classes-in-java
	 *  
 		CREATE FUNCTION    							 --512 (Gets persisted in Metastore)
                        strip AS 'a7_UDF.Strip'
                        USING JAR  
                        'hdfs://localhost:9000/user/mountain/alljars/hivetests-0.0.1-SNAPSHOT.jar';

	 */
	public static class MaximumIntUDAFEvaluator implements UDAFEvaluator {
		private IntWritable result;

		public void init() {
			result = null;
		}

		/*
		 * User agrument is passed as parameter
		 */
		public boolean iterate(IntWritable value) {
			if (value == null) {
				return true;
			}
			if (result == null) {
				result = new IntWritable(value.get());
			} else {
				result.set(Math.max(result.get(), value.get()));
			}
			return true;
		}

		/*
		 * Return Partial Result
		 */
		public IntWritable terminatePartial() {
			return result;
		}

		/*
		 * Merge Partial Result
		 */
		public boolean merge(IntWritable other) {
			return iterate(other);
		}

		/*
		 * Return the Final Result
		 */
		public IntWritable terminate() {
			return result;
		}
	}
}