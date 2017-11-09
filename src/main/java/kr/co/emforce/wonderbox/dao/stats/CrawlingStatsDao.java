package kr.co.emforce.wonderbox.dao.stats;

import java.util.List;

import kr.co.emforce.wonderbox.model.stats.BidCrawlingStats;
import kr.co.emforce.wonderbox.model.stats.BidCrawlingStatsRate;

public interface CrawlingStatsDao {
	public int insertBidCrawlingStats(BidCrawlingStats bidCrawlingStats);
	
	public List<BidCrawlingStatsRate> selectBidCrawlingStatsRate();
}
