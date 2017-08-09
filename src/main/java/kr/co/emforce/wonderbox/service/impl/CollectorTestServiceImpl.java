package kr.co.emforce.wonderbox.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.emforce.wonderbox.dao.CollectorTestDao;
import kr.co.emforce.wonderbox.exception.NumberOfQueryExecutionResultZeroException;
import kr.co.emforce.wonderbox.model.CollectorTest;
import kr.co.emforce.wonderbox.service.CollectorTestService;

@Service
public class CollectorTestServiceImpl implements CollectorTestService{

	private static Logger log = Logger.getLogger(CollectorTestServiceImpl.class);
	
	@Inject
	CollectorTestDao ctDao;
	
	@Override
	public List<CollectorTest> select(Map<String, Object> inputMap) {
		return ctDao.select(inputMap);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int insert(List<CollectorTest> ctList) throws Exception {
		int count = 0;
		
		for(CollectorTest ct : ctList){
			count += ctDao.insert(ct);
			log.info("생성 된 Auto Increment Id : " + ct.getId());
		}
		
		if( count == 0 ) throw new NumberOfQueryExecutionResultZeroException();
		
		return count;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int update(List<CollectorTest> ctList) throws Exception {
		int count = 0;
		
		for(CollectorTest ct : ctList){
			count += ctDao.update(ct);
		}
		
		if( count == 0 ) throw new NumberOfQueryExecutionResultZeroException();

		return count;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int delete(List<CollectorTest> ctList) throws Exception {
		int count = 0;
		
		for(CollectorTest ct : ctList){
			count += ctDao.delete(ct);
		}
		
		if( count == 0 ) throw new NumberOfQueryExecutionResultZeroException();

		return count;
	}

}
