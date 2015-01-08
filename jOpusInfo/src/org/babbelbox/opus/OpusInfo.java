package org.babbelbox.opus;

import java.util.HashMap;
import java.util.Map;

public class OpusInfo {
    private final Map<String, String> metaData;
    private final Map<String, Map<String, String>> streams;
	public OpusInfo(Map<String, String> metaData,
			Map<String, Map<String, String>> streams) {
		super();
		this.metaData = metaData;
		this.streams = streams;
	}
	public Map<String, String> getMetaData() {
		return metaData;
	}
	public Map<String, Map<String, String>> getStreams() {
		return streams;
	}
    
    
}
