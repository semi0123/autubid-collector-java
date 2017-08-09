package kr.co.emforce.wonderbox.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.emforce.wonderbox.dao.CrawlingDao;
import kr.co.emforce.wonderbox.model.BidMachineMng;
import kr.co.emforce.wonderbox.service.CrawlingService;
import kr.co.emforce.wonderbox.util.CurrentTimeUtil;

@Service
public class CrawlingServiceImpl implements CrawlingService{
	
	private static final Logger log = Logger.getLogger(CrawlingServiceImpl.class);
	
	@Inject
	ServletContext ctx;
	
	@Inject
	CrawlingDao crawlingDao;
	
	//@Scheduled(fixedDelay=10000)
	public void TestScheduler(){
		relocateProcessNum();
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void directChangeProcessNum() {
		relocateProcessNum();
	}
	
	private void relocateProcessNum(){
		log.info(CurrentTimeUtil.getCurrentTime() + "자동입찰 키워드 프로세스 재배정 시작");
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("status", "Active");
		List<BidMachineMng> bmmList = crawlingDao.selectStatusFromBidMachine(inputMap);
		inputMap.clear();
		Integer[] bmmArr = new Integer[bmmList.size()];
		Map<Integer, Integer> processCapacity = new HashMap<Integer, Integer>();
		
		Integer numOfMachine = bmmArr.length;
		for(int i=0; i<numOfMachine; i++){
			bmmArr[i] = bmmList.get(i).getProcess_num();
			processCapacity.put(bmmArr[i], 0);
		}
		
		List<LinkedHashMap<String, Object>> kwdList = crawlingDao.selectKwdNmAndTargetFromBidFavoriteKeywords(inputMap);
		
		Integer machineNum = null;
		Random random = new Random();
		Integer curCapacity = null;
		for(Map<String, Object> kwd : kwdList){
			while(true){
				machineNum = bmmArr[random.nextInt(numOfMachine)];
				curCapacity = processCapacity.get(machineNum);
				if( kwd.get("process_num") != machineNum && curCapacity < 100){
					kwd.put("process_num", machineNum);
					processCapacity.put(machineNum, curCapacity+1);
					break;
				}
			}
		}
		
		for(Map<String, Object> kw : kwdList){
			crawlingDao.updateProcessNumFromBidFavoriteKeyword(kw);
		}
	}
	
	@Override
	public List<LinkedHashMap<String, Object>> selectForCrawlingModule(Map<String, Object> inputMap) {
		return crawlingDao.selectKwdNmAndTargetFromBidFavoriteKeywords(inputMap);
	}
	
	
}
