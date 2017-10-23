package kr.co.emforce.wonderbox.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface CrawlingService {
	
	public List<LinkedHashMap<String, Object>> selectForCrawlingModule(Map<String, Object> inputMap);
	
	public void runBidModule(Map<String, Object> requestBody);

	public void directRelocateProcessNum() throws Exception;
	
	public void sendCrawlingPostJsonString(Map<String, Object> requestBody);
	
	public void crash(String name);
	
	public int updateReRun(String processNum);
}
