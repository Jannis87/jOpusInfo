package org.babbelbox.opus;

import java.util.HashMap;
import java.util.Map;

public class OpusInfoFactory {
	private static Map<String, OpusInfoParser> _instances =  new HashMap<>();
	
	public static OpusInfoParser getInstance(String executable) {
		OpusInfoParser result = _instances.get(executable);
		if(result==null) {
			result=new OpusInfoParser(executable);
			_instances.put(executable, result);
		}
		return result;
	}
	public static OpusInfoParser getInstance() {
		return getInstance("opusinfo");
	}
}
