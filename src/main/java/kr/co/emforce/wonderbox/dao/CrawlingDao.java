package kr.co.emforce.wonderbox.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.co.emforce.wonderbox.model.BidFavoriteKeyword;
import kr.co.emforce.wonderbox.model.BidMachineMng;

public interface CrawlingDao {
	public List<LinkedHashMap<String, Object>> selectKwdNmAndTargetFromBidFavoriteKeywords(Map<String, Object> inputMap);
	
	public List<BidFavoriteKeyword> selectAllFromBidFavoritesKeywords(BidFavoriteKeyword bfk);
	
	public List<BidMachineMng> selectStatusFromBidMachine(Map<String, Object> inputMap);
	
	public Map<String, Object> selectOneForBidAmtChangeModule(String kwd_id);
	
	public int updateProcessNumFromBidFavoriteKeyword(Map<String, Object> inputMap);
	
	public int selectSearchAdId(String kwd_nm);
	
	public int updateCrash(int processNum);
}
