package delta.way2.pig;

import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

public class CheckSum extends EvalFunc<String>{

	@Override
	public String exec(Tuple input) throws IOException {
		String checksum = null;
		String str = "";
		
		if(input == null || input.size() == 0) {
			return checksum;
		}
		
		for(int fieldCount = 0; fieldCount < input.size(); fieldCount++) {
			if(input.get(fieldCount) != null) {
				str = str + input.get(fieldCount).toString();
			}
		}
		checksum = DigestUtils.md5Hex(str.trim());
		
		return checksum;
	}

}
