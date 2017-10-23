package kr.co.emforce.wonderbox.service;

import java.util.Map;

import kr.co.emforce.wonderbox.model.BidCrawlingStatsRate;

public interface CrawlingStatsService {
	public int insertBidCrawlingStats(Map<String, Object> requestBody);
	
	public Map<String, BidCrawlingStatsRate> selectBidCrawlingStatsRate();
}
