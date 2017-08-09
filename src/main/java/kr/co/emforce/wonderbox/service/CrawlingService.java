package kr.co.emforce.wonderbox.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface CrawlingService {
	
	public List<LinkedHashMap<String, Object>> selectForCrawlingModule(Map<String, Object> inputMap);
	
	public void directChangeProcessNum();
}
