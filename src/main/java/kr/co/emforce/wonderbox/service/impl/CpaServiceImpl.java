package kr.co.emforce.wonderbox.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kr.co.emforce.wonderbox.dao.collector.AutoBidDao;
import kr.co.emforce.wonderbox.model.collector.BidFavoriteKeyword;
import kr.co.emforce.wonderbox.service.CpaService;
import kr.co.emforce.wonderbox.util.TimePositionMaker;

@Service
public class CpaServiceImpl implements CpaService{

	private static final Logger log = Logger.getLogger(CpaServiceImpl.class);
	
	@Inject
	AutoBidDao autoBidDao;
	
	@Resource(name="anStatsDNS")
	private String anStatsDNS;

	@Override
	public void runBidModule() {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		
		inputMap.put("timePosition", TimePositionMaker.makeTimePosition());
		inputMap.put("bid_status", "CpaActive");
		List<BidFavoriteKeyword> activeBfkList = autoBidDao.selectResvInactCpaKeywordList(inputMap);
		activeBfkList.addAll(autoBidDao.selectResvActKeywordList(inputMap));
		activeBfkList.addAll(autoBidDao.selectResvActCur0KeywordList(inputMap));
		
		RestTemplate restTemplate = new RestTemplate();; 
		Map<String, Object> todayCpa = null;
		
		List<String> args = new ArrayList<String>();
		
		for(BidFavoriteKeyword bfk : activeBfkList){
			todayCpa = (Map<String, Object>) restTemplate.getForObject(anStatsDNS + "/cpa/today/?kwd_id=" + bfk.getKwd_id(), Map.class).get("data");

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
			args.add(String.valueOf(bfk.getMax_bid_amt()));
			args.add(bfk.getEmergency_status());
			
			log.info(args);
		}
		
	}
}
