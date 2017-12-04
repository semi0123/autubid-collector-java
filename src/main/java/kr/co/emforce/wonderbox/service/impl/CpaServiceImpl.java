package kr.co.emforce.wonderbox.service.impl;

import java.io.File;
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
import kr.co.emforce.wonderbox.module.IProcess;
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
		
		try {
			inputMap.put("timePosition", TimePositionMaker.makeTimePosition());
			inputMap.put("bid_status", "CpaActive");
			List<BidFavoriteKeyword> activeBfkList = autoBidDao.selectResvInactCpaKeywordList(inputMap);
			activeBfkList.addAll(autoBidDao.selectResvActKeywordList(inputMap));
			activeBfkList.addAll(autoBidDao.selectResvActCur0KeywordList(inputMap));
			
			RestTemplate restTemplate = new RestTemplate();; 
			Map<String, Object> todayCpa = null;
			
			List<String> args = new ArrayList<String>();
			
			String tempRecClkRnk = null;
			String tempRecClkAt = null;
			
			
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
				args.add(String.valueOf(autoBidDao.selectOneBidFavoriteKeyword(bfk.getKwd_id()).getCur_cpa_amt()));
				
				log.info("IProcess.MODULES_DIR => " + IProcess.MODULES_DIR);
				log.info("IProcess.AUTO_BID_WORKER => " + IProcess.CPA_AUTO_BID_WORKER);
				log.info("args => ");
				
				int cnt = 0;
				for(String temp : args){
					log.info("["+cnt+"] "+temp);
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
	
}
