package kr.co.emforce.wonderbox.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.emforce.wonderbox.dao.CollectorTestDao;
import kr.co.emforce.wonderbox.model.CollectorTest;
import kr.co.emforce.wonderbox.service.CollectorTestService;

@Service
public class CollectorTestServiceImpl implements CollectorTestService{

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
			System.out.println("생성 된 Auto Increment Id : " + ct.getId());
		}
		
		return count;
	}

	@Override
	public int update(CollectorTest ct) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(CollectorTest ct) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
