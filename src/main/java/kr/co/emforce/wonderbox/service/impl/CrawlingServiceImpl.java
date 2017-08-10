package kr.co.emforce.wonderbox.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
	
	@Inject
	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * cron 사용법 
	 * 순서별 의미 초(0~59) 분(0~59) 시(0~23) 날짜(1~31) 달(1~12) 요일(1~7) 년(1970~2099)
	 * 사용 예
	 * 0 0 12 * * *       ==> 매일 12시에 실행
	 * 0 15 10 * * *      ==> 매일 10시 15분에 실행
	 * 0 * 14 * * *       ==> 매일 14시에 실행
	 * 0 0/5 14 18 * * *  ==> 매일 14시, 18시에 시작해서 5분간격으로 실행
	 * 0 0-5 14 * * *     ==> 매일 14시에 시작해서 0분동안 실행 
	 */
	@Scheduled(cron="0 10 0 * * *") // 0시 10분에 프로세스넘버 재배치
	public void TestScheduler() {
		try{ relocateProcessNum("(자동)"); }catch(Exception e){ }
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void directChangeProcessNum() throws Exception {
		relocateProcessNum("(수동)");
	}
	
	private void relocateProcessNum(String callType) throws Exception{
		log.info(CurrentTimeUtil.getCurrentTime() + callType + " 자동입찰 키워드 프로세스 재배정 시작");
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("status", "Active");
		List<BidMachineMng> bmmList = crawlingDao.selectStatusFromBidMachine(inputMap);
		inputMap.clear();
		Integer[] bmmArr = new Integer[bmmList.size()];
		Map<Integer, Integer> processCapacity = new HashMap<Integer, Integer>();
		
		int totalCount = 0;
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
					totalCount++;
					break;
				}
			}
		}
		
		// 직접 트랜잭션 관리
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try{
			for(Map<String, Object> kw : kwdList){
				sqlSession.update(CrawlingDao.class.getName() + ".updateProcessNumFromBidFavoriteKeyword", kw);
			}
			sqlSession.commit();
			log.info(CurrentTimeUtil.getCurrentTime() + callType + " 자동입찰 키워드 재배정 완료 / 업데이트 된 키워드 수 : " + totalCount);
		}catch(Exception e){
			sqlSession.rollback();
			log.error(CurrentTimeUtil.getCurrentTime() + e.getMessage());
			log.error(CurrentTimeUtil.getCurrentTime()  + callType + " 자동입찰 키워드 재배정 실패");
			throw e;
		}finally{
			try{ if( sqlSession != null ) sqlSession.close(); }catch(Exception e){ }
		}
	}
	
	@Override
	public List<LinkedHashMap<String, Object>> selectForCrawlingModule(Map<String, Object> inputMap) {
		return crawlingDao.selectKwdNmAndTargetFromBidFavoriteKeywords(inputMap);
	}
	
	
}
