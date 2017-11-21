package kr.co.emforce.wonderbox.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import kr.co.emforce.wonderbox.dao.collector.CrawlingDao;
import kr.co.emforce.wonderbox.model.collector.BidFavoriteKeyword;
import kr.co.emforce.wonderbox.model.collector.BidInstance;
import kr.co.emforce.wonderbox.model.collector.BidMachineMng;
import kr.co.emforce.wonderbox.model.collector.CrawlingResult;
import kr.co.emforce.wonderbox.module.IProcess;
import kr.co.emforce.wonderbox.service.CrawlingService;
import kr.co.emforce.wonderbox.util.CurrentTimeUtil;
import kr.co.emforce.wonderbox.util.HistoryUtil;
import kr.co.emforce.wonderbox.util.JsonToClassConverter;



@Service
public class CrawlingServiceImpl implements CrawlingService{
	
	private static final Logger log = Logger.getLogger(CrawlingServiceImpl.class);
	
	@Inject
	ServletContext ctx;
	
	@Inject
	CrawlingDao crawlingDao;
	
	@Resource(name="collectorSqlSessionFactory")
	private SqlSessionFactory collectorSqlSessionFactory;
	
	@Resource(name="statsSqlSessionFactory")
	private SqlSessionFactory statsSqlSessionFactory;
	
	@Autowired
	JavaMailSender mailSender;
	
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
		
		Date now = new Date();
		// 스케쥴 월요일부터 시작
		Integer timePosition = ((now.getDay() == 0) ? 6 : now.getDay()-1) * 24 + now.getHours() + 1; // MYSQL Split Index 1부터 시작
		Map<String, Object> inputMap = null;
		try{
			crawlingMap = JsonToClassConverter.convertToIdMap((ArrayList<Map<String, Object>>) requestBody.get("result_rank"), "site", CrawlingResult.class);
			activeBfkList = crawlingDao.selectRankKeywordList(new BidFavoriteKeyword().setKwd_nm(kwd_nm)
																					  .setTarget(target)
																					  .setEmergency_status(emergency_status));
			inputMap = new HashMap<String, Object>();
			inputMap.put("bid_status", "Active");
			inputMap.put("timePosition", timePosition);
			inputMap.put("emergency_status", emergency_status);
			// 예약상태가 ON이면서 현재 시간대 순위기반인키워드 추출
			activeBfkList.addAll(crawlingDao.selectResvStatusActiveKeywordList(inputMap));
			
			Integer rank = null;
			Integer opp_rank = null;
			
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
				
				
				rank= 16;
				try {
					rank = Integer.valueOf(String.valueOf(crawlingMap.get(joinSelectMap.get("site")).getRank()));
					log.info("rank : " + rank);
				}catch(Exception e) {
					log.info("===== : " + e.getMessage());
					rank= 16;
				}
				
				log.info("checked_at : " + checked_at);
				String advId = String.valueOf(joinSelectMap.get("adv_id"));
				String customerId = String.valueOf(joinSelectMap.get("na_account_ser"));
				String kwdId = String.valueOf(joinSelectMap.get("kwd_id"));
				Integer before_rank = Integer.valueOf(String.valueOf(joinSelectMap.get("rank")));
				
				Integer rankRange = Integer.valueOf(String.valueOf(crawlingMap.size()));
				String maxBidAmt = String.valueOf(joinSelectMap.get("max_bid_amt"));
//				String emergencyStatus = String.valueOf(joinSelectMap.get("emergency_status"));
				String goalRank = String.valueOf(joinSelectMap.get("goal_rank"));
				
				String bid_type = null;
				if( "Active".equals(joinSelectMap.get("bid_status"))){
					bid_type = "rank";
				}else if( "OppActive".equals(joinSelectMap.get("bid_status"))){
					bid_type = "opp";
				}
				
				if( "OppActive".equals(joinSelectMap.get("bid_status")) ){
					opp_rank = 16;
					try{
						opp_rank = crawlingMap.get(joinSelectMap.get("opp_site")).getRank();
						log.info("opp rank : " + opp_rank);
						Integer tempGoalRank = opp_rank + Integer.parseInt(joinSelectMap.get("opp_gap").toString());
						if( tempGoalRank > rankRange){
							tempGoalRank = rankRange;
						}
						goalRank = tempGoalRank.toString();
					}catch(Exception e){
						log.info("===== : " + e.getMessage());
						opp_rank = 16;
					}
				}
				
				if( before_rank != rank ) {
					log.info("TODO Write History :");
					log.info(" | customerId : " + customerId+ " |  kwdId : " + kwdId+ " |  kwd_nm : " + kwd_nm + " | checked_at : " + checked_at + " | emergency_status:" + emergency_status); 
					
					String write_msg = "현재 순위 : "+before_rank + " > " + rank ;
					String user_id = "시스템 ";
					String type_desc = "자동";
					HistoryUtil.writekwdBidHistories(customerId, kwdId, kwd_nm, type_desc, write_msg, user_id, checked_at,emergency_status);
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
				args.add(emergency_status);
				args.add(opp_rank.toString());
				args.add(bid_type);
				
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
				// 프로세스넘버별 130개까지
				if( kwd.get("process_num") != machineNum && curCapacity < 130){
					kwd.put("process_num", machineNum);
					processCapacity.put(machineNum, curCapacity+1);
					totalCount++;
					break;
				}
			}
		}
		
		// SqlSessionFactory를 이용한 수동 트랜잭션 관리
		SqlSession sqlSession = collectorSqlSessionFactory.openSession(ExecutorType.BATCH, false);
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
	
	@Override
	public void crash(String name) {
		if( crawlingDao.updateCrash(name) == 1 ){
			Map<String, Object> inputMap = new HashMap<String, Object>();
			inputMap.put("name", name);
			StringBuffer content = new StringBuffer();
			content.append("\n\n* 해당 메일은 입찰 솔루션 오류 발생시 자동으로 발송되는 메일입니다.\n\n");
			BidInstance instance = crawlingDao.selectBidInstance(name);
			content.append(instance.getDesc()+"\n\n")
				   .append("name : " + instance.getName() + "\n\n")
				   .append("label : " + instance.getLabel() + "\n\n")
				   .append("ip_v4 : " + instance.getIp_v4() + "\n\n");
			SimpleMailMessage smm = new SimpleMailMessage();
			smm.setFrom("jungyw@emforce.co.kr");
			smm.setTo(new String[] {"ahnjaemo@emforce.co.kr", "jungyw@emforce.co.kr", "gusfla09@emforce.co.kr"});
			smm.setSubject("자동입찰 솔루션 오류");
			smm.setText(content.toString());
			mailSender.send(smm);
			log.error(name + " Crawling Error Send Mail");
		}
	}
	
	@Override
	public int updateReRun(String processNum) {
		if( "All".equals(processNum) ){
			processNum = null;
		}
		return crawlingDao.updateReRun(processNum);
	}
}
