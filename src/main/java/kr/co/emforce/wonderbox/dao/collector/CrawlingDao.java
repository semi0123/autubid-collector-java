package kr.co.emforce.wonderbox.dao.collector;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.co.emforce.wonderbox.model.collector.BidFavoriteKeyword;
import kr.co.emforce.wonderbox.model.collector.BidInstance;
import kr.co.emforce.wonderbox.model.collector.BidMachineMng;
import kr.co.emforce.wonderbox.model.stats.BidCrawlingStats;

public interface CrawlingDao {
	public List<LinkedHashMap<String, Object>> selectKwdNmAndTargetFromBidFavoriteKeywords(Map<String, Object> inputMap);
	
	public List<BidFavoriteKeyword> selectAllFromBidFavoritesKeywords(BidFavoriteKeyword bfk);
	
	public List<BidMachineMng> selectStatusFromBidMachine(Map<String, Object> inputMap);
	
	public Map<String, Object> selectOneForBidAmtChangeModule(String kwd_id);
	
	public int updateProcessNumFromBidFavoriteKeyword(Map<String, Object> inputMap);
	
	public int selectSearchAdId(String kwd_nm);
	
	public int updateCrash(String name);
	
	public List<BidFavoriteKeyword> selectKeywordsInCrashedMachine(Integer processNum);
	
	public int updateReRun(String processNum);
	
	public BidInstance selectBidInstance(String name);
}
