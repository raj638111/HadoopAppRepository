package a7_UDF;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/*
 * 	Strip Leading & Trailing empty spaces
 * 
 * 
 	CREATE FUNCTION    							 --512 (Gets persisted in Metastore)
                        strip AS 'a7_UDF.Strip'
                        USING JAR  
                        'hdfs://localhost:9000/user/mountain/alljars/hivetests-0.0.1-SNAPSHOT.jar';
        
 */
public class Strip extends UDF {
	private Text result = new Text();

	/*
	 *	A UDF must implement atleast on 'evaluate' method	--512
	 *  NOTE : 	String can also be used as argument, but Text
	 *  		brings the advantage of Object reuse
	 *  ** Hive also supports other primitive types like List & Map
	 */
	public Text evaluate(Text str) {
		if (str == null) {
			return null;
		}
		result.set(StringUtils.strip(str.toString()));
		return result;
	}

	public Text evaluate(Text str, String stripChars) {
		if (str == null) {
			return null;
		}
		result.set(StringUtils.strip(str.toString(), stripChars));
		return result;
	}
}