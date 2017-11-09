package kr.co.emforce.wonderbox.service;

import java.util.Map;

import kr.co.emforce.wonderbox.model.stats.BidCrawlingStatsRate;

public interface CrawlingStatsService {
	public int insertBidCrawlingStats(Map<String, Object> requestBody);
	
	public Map<String, BidCrawlingStatsRate> selectBidCrawlingStatsRate();
}
