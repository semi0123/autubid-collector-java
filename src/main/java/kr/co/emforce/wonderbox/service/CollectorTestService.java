package kr.co.emforce.wonderbox.service;

import java.util.List;
import java.util.Map;

import kr.co.emforce.wonderbox.model.CollectorTest;

public interface CollectorTestService {
	
	public List<CollectorTest> select(Map<String, Object> inputMap);
	
	public int insert(List<CollectorTest> ctList) throws Exception;
	
	public int update(List<CollectorTest> ctList) throws Exception;
	
	public int delete(List<CollectorTest> ctList) throws Exception;
}
