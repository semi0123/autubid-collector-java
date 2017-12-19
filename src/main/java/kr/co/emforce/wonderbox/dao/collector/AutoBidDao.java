package kr.co.emforce.wonderbox.dao.collector;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.co.emforce.wonderbox.model.collector.BidFavoriteKeyword;
import kr.co.emforce.wonderbox.model.collector.BidInstance;
import kr.co.emforce.wonderbox.model.collector.BidMachineMng;

public interface AutoBidDao {
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
	
	
	
	public BidFavoriteKeyword selectOneBidFavoriteKeyword(String kwd_id);
	
	// 현재 CPA 값 업데이트(kwd_id, cpa)
	public int updateCurCpaAmtOneBidFavoriteKeyword(Map<String, Object> inputMap);
	
	// 즐겨찾기 된 전체 키워드 가져오기(premium만)
	public List<BidFavoriteKeyword> selectAllBidFavoriteKeywords();
	
	// 크롤링 해올 키워드 목록
	public List<LinkedHashMap<String, Object>> selectCrawlingRankKwdList(Map<String, Object> inputMap);

	// 순위기반용
	public List<BidFavoriteKeyword> selectResvInactRankKeywordList(BidFavoriteKeyword bfk);
	
	// Cpa용
	public List<BidFavoriteKeyword> selectResvInactCpaKeywordList(Map<String, Object> inputMap);
	
	// 공통사용
	public List<BidFavoriteKeyword> selectResvActCur0KeywordList(Map<String, Object> inputMap);
	public List<BidFavoriteKeyword> selectResvActKeywordList(Map<String, Object> inputMap);
	
	
}
