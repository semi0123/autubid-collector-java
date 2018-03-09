package kr.co.emforce.wonderbox.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.emforce.wonderbox.dao.collector.AutoBidDao;
import kr.co.emforce.wonderbox.model.collector.BidFavoriteKeyword;
import kr.co.emforce.wonderbox.module.IProcess;
import kr.co.emforce.wonderbox.service.CpaService;
import kr.co.emforce.wonderbox.util.RestTemplateUtil;
import kr.co.emforce.wonderbox.util.TimePositionMaker;

@Service
public class CpaServiceImpl implements CpaService {

	private static final Logger log = Logger.getLogger(CpaServiceImpl.class);

	@Inject
	AutoBidDao autoBidDao;

	@Resource(name = "anStatsDNS")
	private String anStatsDNS;

	@Override
	public void runBidModule() {
		Map<String, Object> inputMap = new HashMap<String, Object>();

		try {
			inputMap.put("timePosition", TimePositionMaker.makeTimePosition());
			inputMap.put("bid_status", "CpaActive");
			List<BidFavoriteKeyword> activeBfkList = autoBidDao.selectResvInactCpaKeywordList(inputMap);
			activeBfkList.addAll(autoBidDao.selectResvActKeywordList(inputMap));
			activeBfkList.addAll(autoBidDao.selectResvActCur0KeywordList(inputMap));
			
			Map<String, Object> todayCpa = null;

			List<String> args = new ArrayList<String>();
			
			Integer max_bid_amt = 0;
			Integer min_bid_amt = 0;
			
			
			for (BidFavoriteKeyword bfk : activeBfkList) {

				todayCpa = (Map<String, Object>) RestTemplateUtil.exchange(anStatsDNS + "/cpa/today/", HttpMethod.GET, "kwd_id=" + bfk.getKwd_id()).get("data");
				args.clear();
				args.add(bfk.getAdv_id());
				args.add(bfk.getNa_account_ser());
				args.add(bfk.getKwd_id());
				args.add(String.valueOf(bfk.getRec_clk_rnk()));
				args.add(String.valueOf(bfk.getRec_clk_at()));
				args.add(bfk.getTarget());
				args.add(todayCpa.get("today_cost").toString());
				args.add(todayCpa.get("today_conv").toString());
				args.add(String.valueOf(bfk.getGoal_cpa_amt()));
				
				// 0일 경우 10만 처리
				max_bid_amt = bfk.getMax_bid_amt() == 0 ? 100000 : bfk.getMax_bid_amt();
				args.add(String.valueOf(max_bid_amt));
				
				// 0일 경우 70 처리
				min_bid_amt = bfk.getMin_bid_amt() == 0 ? 70 : bfk.getMin_bid_amt();
				args.add(String.valueOf(min_bid_amt));
				
				args.add(bfk.getEmergency_status());
//				args.add(String.valueOf(autoBidDao.selectOneBidFavoriteKeyword(bfk.getKwd_id()).getCur_cpa_amt()));
				args.add(bfk.getIs_resv());

				log.info("IProcess.MODULES_DIR => " + IProcess.MODULES_DIR);
				log.info("IProcess.AUTO_BID_WORKER => " + IProcess.CPA_AUTO_BID_WORKER);
				log.info("args => ");

				int cnt = 0;
				for (String temp : args) {
					log.info("[" + cnt + "] " + temp);
					cnt++;
				}
				runModule(IProcess.MODULES_DIR, IProcess.CPA_AUTO_BID_WORKER, args);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runModule(String modPath, String modName, List<String> arguments) throws Exception {
		arguments.add(0, modName);

		ProcessBuilder pb = new ProcessBuilder(arguments);
		pb.directory(new File(modPath));
		pb.start();
	}
	
	@Override
	public void runAllKeyword() {
		List<BidFavoriteKeyword> allBidFavoriteKeyword = autoBidDao.selectAllBidFavoriteKeywords();
		Map<String, Object> responseData = null;
		int todayCost = 0;
		int todayConv = 0;
		int twoDayCost = 0;
		int twoDayConv = 0;
		int totalCost = 0;
		int totalConv = 0;
		int calculatedCpa = 0;
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		for (BidFavoriteKeyword bfk : allBidFavoriteKeyword){
			try{
				data.put("kwd_id", bfk.getKwd_id());
				
				// 오늘 CPA
				responseData = (Map<String, Object>) RestTemplateUtil.exchange(anStatsDNS + "/cpa/today/", HttpMethod.GET, data).get("data");
				todayCost = Integer.parseInt(responseData.get("today_cost").toString());
				todayConv = Integer.parseInt(responseData.get("today_conv").toString());
				
				// 어제 그제 CPA
				data.put("day", 6);
				responseData = (Map<String, Object>) RestTemplateUtil.exchange(anStatsDNS + "/cpa/history/days/", HttpMethod.GET, data).get("data");
				twoDayCost = Integer.parseInt(responseData.get("cost").toString());
				twoDayConv = Integer.parseInt(responseData.get("conv").toString());
				
				totalCost = todayCost + twoDayCost;
				totalConv = todayConv + twoDayConv;
				calculatedCpa = totalConv == 0 ? 0 : totalCost / totalConv;
				
				data.clear();
				data.put("kwd_id", bfk.getKwd_id());
				data.put("kwd_nm", bfk.getKwd_nm());
				data.put("cpa", calculatedCpa);
				responseData = RestTemplateUtil.exchange(anStatsDNS + "/cpa/history/", HttpMethod.POST, data);
				if( responseData.get("success").equals(Boolean.TRUE) ){
					// 2018-01-29 Cpa 계산 오류로 주석 처리
					//autoBidDao.updateCurCpaAmtOneBidFavoriteKeyword(requestBody);
					log.info("■■■■■■■■■■■■■■■■■ cpa run all keyword success");
					log.info("kwd_id : " + bfk.getKwd_id() + " / kwd_nm : " + bfk.getKwd_nm() + " / cpa : " + calculatedCpa);
				}else{
					throw new Exception("POST " + anStatsDNS + "/cpa/history/ failed");
				}
				
			}catch(Exception e){
				log.error("■■■■■■■■■■■■■■■■■ cpa run all keyword error");
				log.error("kwd_id : " + bfk.getKwd_id() + " / kwd_nm : " + bfk.getKwd_nm());
//				e.printStackTrace();
			}
			
		}
	}

}
