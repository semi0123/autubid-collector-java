package kr.co.emforce.wonderbox.dao;

import java.util.List;

import kr.co.emforce.wonderbox.model.BidCrawlingStats;
import kr.co.emforce.wonderbox.model.BidCrawlingStatsRate;

public interface CrawlingStatsDao {
	public int insertBidCrawlingStats(BidCrawlingStats bidCrawlingStats);
	
	public List<BidCrawlingStatsRate> selectBidCrawlingStatsRate();
}
