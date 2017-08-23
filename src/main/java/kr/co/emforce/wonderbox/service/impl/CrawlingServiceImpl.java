package kr.co.emforce.wonderbox.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
import kr.co.emforce.wonderbox.module.IProcess;
import kr.co.emforce.wonderbox.service.CrawlingService;
import kr.co.emforce.wonderbox.util.CurrentTimeUtil;
import kr.co.emforce.wonderbox.util.HistoryUtil;
import kr.co.emforce.wonderbox.util.JsonToClassConverter;


import java.util.Iterator;



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
		String emergency_status = requestBody.get("emergency_status").toString();
		ArrayList<Map<String,Object>> rnk_list = (ArrayList<Map<String, Object>>) requestBody.get("result_rank");

		Map<Object, CrawlingResult> crawlingMap = null;
		List<BidFavoriteKeyword> activeBfkList = null;
		Map<String, Object> joinSelectMap = null;
		
		try{
			crawlingMap = JsonToClassConverter.convertToIdMap((ArrayList<Map<String, Object>>) requestBody.get("result_rank"), "site", CrawlingResult.class);
			activeBfkList = crawlingDao.selectAllFromBidFavoritesKeywords(new BidFavoriteKeyword().setKwd_nm(kwd_nm).setTarget(target).setBid_status("Active").setEmergency_status(emergency_status));
			for(BidFavoriteKeyword bfk : activeBfkList){
				joinSelectMap = crawlingDao.selectOneForBidAmtChangeModule(bfk.getKwd_id());
				
//				log.info(joinSelectMap);
				
//				log.info("adv_id : " + joinSelectMap.get("adv_id"));
//				log.info("kwd_id : " + joinSelectMap.get("kwd_id"));
//				log.info("kwd_nm : " + kwd_nm);
//				log.info("target : " + target);
//				log.info("na_account_ser : " + joinSelectMap.get("na_account_ser"));
//				log.info("goal_rank : " + joinSelectMap.get("goal_rank"));
//				log.info("rank_range : " + crawlingMap.size());
//				log.info("max_bid_amt : " + joinSelectMap.get("max_bid_amt"));
//				log.info("emergency_status : " + joinSelectMap.get("emergency_status"));
//				log.info("cur_rank 1: " + bfk.getRank());
//				log.info("cur_rank 2: " + joinSelectMap.get("rank"));
//				log.info("rank : " + crawlingMap.get(joinSelectMap.get("site")).getRank());
//				log.info("checked_at : " + checked_at);
				String advId = String.valueOf(joinSelectMap.get("adv_id"));
				String customerId = String.valueOf(joinSelectMap.get("na_account_ser"));
				String kwdId = String.valueOf(joinSelectMap.get("kwd_id"));
				Integer before_rank = Integer.valueOf(String.valueOf(joinSelectMap.get("rank")));
				Integer rank = Integer.valueOf(String.valueOf(crawlingMap.get(joinSelectMap.get("site")).getRank()));
				if( rank == null ) rank = 0;
				Integer rankRange = Integer.valueOf(String.valueOf(crawlingMap.size()));
				String goalRank = String.valueOf(joinSelectMap.get("goal_rank"));
				String maxBidAmt = String.valueOf(joinSelectMap.get("max_bid_amt"));
				String emergencyStatus = String.valueOf(joinSelectMap.get("emergency_status"));
				
				if( before_rank != rank ) {
					log.info("TODO Write History :");
					log.info("customerId : " + customerId+ " kwdId : " + kwdId+ " kwd_nm : " + kwd_nm + "checked_at : " + checked_at ); 
					
					String write_msg = "현재 순위 : "+before_rank + " > " + rank ;
					String user_id = "시스템 ";
					String type_desc = "자동";
					HistoryUtil.writekwdBidHistories(customerId, kwdId, kwd_nm, type_desc, write_msg, user_id, checked_at);
				}

				
				List<String> args = new ArrayList<String>();
				args.add(advId);
				args.add(customerId);
				args.add(kwdId);
				args.add(target);
				args.add(rank.toString());
				args.add(rankRange.toString());
				args.add(goalRank);
				args.add(checked_at);
				args.add(maxBidAmt);
				args.add(emergencyStatus);
				
				log.info("IProcess.MODULES_DIR => " + IProcess.MODULES_DIR);
				log.info("IProcess.AUTO_BID_WORKER => " + IProcess.AUTO_BID_WORKER);
				log.info("args => ");
				
				int cnt = 0;
				for(String temp : args){
					log.info("["+cnt+"] "+temp);
				  	cnt++;
				}

				runModule(IProcess.MODULES_DIR, IProcess.AUTO_BID_WORKER, args);
			}
			
			try {
				
				int search_ad_id = crawlingDao.selectSearchAdId(kwd_nm);
				log.info("rank history search_ad_id : " + search_ad_id);
				HistoryUtil.writekwdRankHistories(IProcess.RANK_HISTORY_DIR, kwd_nm, target, checked_at, emergency_status, search_ad_id, rnk_list);
				
			}catch(Exception e) {
				log.info("rank history error : " + e.getMessage());
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
		log.info(CurrentTimeUtil.getCurrentTime() + "Active 상태인 프로세스 개수 : " + numOfMachine);
		if( numOfMachine == 0 ){
			log.error(CurrentTimeUtil.getCurrentTime() + "Active인 프로세스가 없으므로 재배정 실패");
			return;
		}
					
		
		for(int i=0; i<numOfMachine; i++){
			bmmArr[i] = bmmList.get(i).getProcess_num();
			processCapacity.put(bmmArr[i], 0);
		}
		
		List<LinkedHashMap<String, Object>> kwdList = crawlingDao.selectKwdNmAndTargetFromBidFavoriteKeywords(inputMap);
		
		Integer machineNum = null;
		Random random = new Random();
		Integer curCapacity = null;
		for(Map<String, Object> kwd : kwdList){
			if( numOfMachine == 1 ){
				kwd.put("process_num", bmmArr[0]);
				totalCount++;
			}else{
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
	
	public void runModule(String modPath, String modName, List<String> arguments) throws Exception {
	    arguments.add(0, modName);
	
	    ProcessBuilder pb = new ProcessBuilder(arguments);
	    pb.directory(new File(modPath));
	    pb.start();
	}	
}
