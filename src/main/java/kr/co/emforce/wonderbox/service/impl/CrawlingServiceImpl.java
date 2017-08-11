package kr.co.emforce.wonderbox.service.impl;

import java.util.ArrayList;
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
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import kr.co.emforce.wonderbox.dao.CrawlingDao;
import kr.co.emforce.wonderbox.model.BidFavoriteKeyword;
import kr.co.emforce.wonderbox.model.BidMachineMng;
import kr.co.emforce.wonderbox.model.CrawlingResult;
import kr.co.emforce.wonderbox.service.CrawlingService;
import kr.co.emforce.wonderbox.util.CurrentTimeUtil;
import kr.co.emforce.wonderbox.util.JsonToClassConverter;

@Service
public class CrawlingServiceImpl implements CrawlingService{
	
	private static final Logger log = Logger.getLogger(CrawlingServiceImpl.class);
	
	@Inject
	ServletContext ctx;
	
	@Inject
	CrawlingDao crawlingDao;
	
	@Inject
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public List<LinkedHashMap<String, Object>> selectForCrawlingModule(Map<String, Object> inputMap) {
		return crawlingDao.selectKwdNmAndTargetFromBidFavoriteKeywords(inputMap);
	}
	
	@Override
	public void runBidModule(Map<String, Object> requestBody) {
		
		String kwd_nm = requestBody.get("kwd_nm").toString();
		String target = requestBody.get("target").toString();
		String checked_at = requestBody.get("checked_at").toString();
		
		Map<Object, CrawlingResult> crawlingMap = null;
		List<BidFavoriteKeyword> activeBfkList = null;
		Map<String, Object> joinSelectMap = null;
		
		try{
			crawlingMap = JsonToClassConverter.convertToIdMap((ArrayList<Map<String, Object>>) requestBody.get("result_rank"), "site", CrawlingResult.class);
			activeBfkList = crawlingDao.selectAllFromBidFavoritesKeywords(new BidFavoriteKeyword().setKwd_nm(kwd_nm).setTarget(target).setBid_status("Active"));
			for(BidFavoriteKeyword bfk : activeBfkList){
				joinSelectMap = crawlingDao.selectOneForBidAmtChangeModule(bfk.getKwd_id());
				log.info("adv_id : " + joinSelectMap.get("adv_id"));
				log.info("kwd_id : " + joinSelectMap.get("kwd_id"));
				log.info("kwd_nm : " + kwd_nm);
				log.info("target : " + target);
				log.info("na_account_ser : " + joinSelectMap.get("na_account_ser"));
				log.info("goal_rank : " + joinSelectMap.get("goal_rank"));
				log.info("rank_range : " + crawlingMap.size());
				log.info("max_bid_amt : " + joinSelectMap.get("max_bid_amt"));
				log.info("emergency_status : " + joinSelectMap.get("emergency_status"));
				log.info("rank : " + crawlingMap.get(joinSelectMap.get("site")).getRank());
				log.info("checked_at : " + checked_at);
			}
		}catch(Exception e){
			log.info(e.getMessage());
		}
	}
	
	@Override
	public void sendCrawlingPostJsonString(Map<String, Object> requestBody) {
		log.info(new JSONObject(requestBody).toString());
	}

	@Override
	public void directRelocateProcessNum() throws Exception {
		log.info(CurrentTimeUtil.getCurrentTime() + "(수동) 자동입찰 키워드 프로세스 재배정 시작");
		
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
		
		// SqlSessionFactory를 이용한 수동 트랜잭션 관리
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try{
			for(Map<String, Object> kw : kwdList){
				sqlSession.update(CrawlingDao.class.getName() + ".updateProcessNumFromBidFavoriteKeyword", kw);
			}
			sqlSession.commit();
			log.info(CurrentTimeUtil.getCurrentTime() + "(수동) 자동입찰 키워드 재배정 완료 / 업데이트 된 키워드 수 : " + totalCount);
		}catch(Exception e){
			sqlSession.rollback();
			log.error(CurrentTimeUtil.getCurrentTime() + e.getMessage());
			log.error(CurrentTimeUtil.getCurrentTime()  + "(수동) 자동입찰 키워드 재배정 실패");
			throw e;
		}finally{
			try{ if( sqlSession != null ) sqlSession.close(); }catch(Exception e){ }
		}
	}
	
}
