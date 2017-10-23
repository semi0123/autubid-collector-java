package kr.co.emforce.wonderbox.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.co.emforce.wonderbox.dao.CrawlingStatsDao;
import kr.co.emforce.wonderbox.model.BidCrawlingStats;
import kr.co.emforce.wonderbox.model.BidCrawlingStatsRate;
import kr.co.emforce.wonderbox.service.CrawlingStatsService;
import kr.co.emforce.wonderbox.util.CurrentTimeUtil;
import kr.co.emforce.wonderbox.util.JsonToClassConverter;

@Service
public class CrawlingStatsServiceImpl implements CrawlingStatsService{
	
	private static final Logger log = Logger.getLogger(CrawlingStatsServiceImpl.class);
	
	@Inject
	CrawlingStatsDao crawlingStatsDao;
	
	@Override
	public int insertBidCrawlingStats(Map<String, Object> requestBody) {
		try{
			BidCrawlingStats bidCrawlingStats = JsonToClassConverter.convert(requestBody,BidCrawlingStats.class);
			bidCrawlingStats.setCreated_by("SYSTEM").setUpdated_by("SYSTEM");
			crawlingStatsDao.insertBidCrawlingStats(bidCrawlingStats);
		}catch(Exception e){
			log.error("Insert bid_crawling_stats Error");
			log.error(CurrentTimeUtil.getCurrentTime() + e.getMessage());
		}
		return 0;
	}
	
	@Override
	public Map<String, BidCrawlingStatsRate> selectBidCrawlingStatsRate() {
		Map<String, BidCrawlingStatsRate> resultMap = new LinkedHashMap<>();
		List<BidCrawlingStatsRate> rateList = crawlingStatsDao.selectBidCrawlingStatsRate();
		// lambda
		rateList.forEach(r -> resultMap.put(r.getServer_name(), r));
		return resultMap;
	}
}
