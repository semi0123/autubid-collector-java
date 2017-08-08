package kr.co.emforce.wonderbox.dao;

import java.util.List;
import java.util.Map;

import kr.co.emforce.wonderbox.model.CollectorTest;

public interface CollectorTestDao {
	
	public List<CollectorTest> select(Map<String, Object> inputMap);
	
	public int insert(CollectorTest ct);
	
	public int update(CollectorTest ct);
	
	public int delete(CollectorTest ct);
}
