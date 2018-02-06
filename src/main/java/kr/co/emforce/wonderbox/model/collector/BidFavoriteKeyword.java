package kr.co.emforce.wonderbox.model.collector;

import java.sql.Timestamp;

public class BidFavoriteKeyword {
	
	private Integer id;
	private String created_by;
	private String updated_by;
	private Integer ad_med_id;
	private String campaign_id;
	private String campaign_nm;
	private String adgroup_id;
	private String adgroup_nm;
	private String kwd_id;
	private String kwd_nm;
	private Integer bid_favorites_id;
	private String bid_status;
	private Integer goal_rank;
	private Integer max_bid_amt;
	private Integer rank;
	private Integer rank_range;
	private Timestamp last_rank_at;
	private Integer cur_bid_amt;
	private Integer before_bid_amt;
	private Timestamp bid_updated_at;
	private Timestamp created_at;
	private Timestamp updated_at;
	private String site;
	private String target;
	private Integer process_num;
	private String emergency_status;
	
	// 조인컬럼
	private String adv_id;
	private String na_account_ser;
	
	// 모듈에 예약 상태 키워드인지 넘겨주는 컬럼
	private String is_resv;
	
	// 2차 추가 컬럼
	private String bid_cpa_status;
	private Integer cur_cpa_amt;
	private Integer goal_cpa_amt;
	private Integer rec_clk_rnk;
	private Timestamp rec_clk_at;
	private Integer cur_opp_rank;
	private String opp_site;
	private Integer opp_gap;
	private String resv_status;
	private String msg_cd;
	
	// 3차 추가 컬럼
	private Integer min_bid_amt;
	private Integer opp_goal_rank;
	private Double bid_success_rate;
	
	public String getAdv_id() {
		return adv_id;
	}
	
	public BidFavoriteKeyword setAdv_id(String adv_id) {
		this.adv_id = adv_id;
		return this;
	}
	
	public String getNa_account_ser() {
		return na_account_ser;
	}
	
	public BidFavoriteKeyword setNa_account_ser(String na_account_ser) {
		this.na_account_ser = na_account_ser;
		return this;
	}
	
	public Integer getId() {
		return id;
	}
	
	public BidFavoriteKeyword setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public String getCreated_by() {
		return created_by;
	}
	
	public BidFavoriteKeyword setCreated_by(String created_by) {
		this.created_by = created_by;
		return this;
	}
	
	public String getUpdated_by() {
		return updated_by;
	}
	
	public BidFavoriteKeyword setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
		return this;
	}
	
	public Integer getAd_med_id() {
		return ad_med_id;
	}
	
	public BidFavoriteKeyword setAd_med_id(Integer ad_med_id) {
		this.ad_med_id = ad_med_id;
		return this;
	}
	
	public String getCampaign_id() {
		return campaign_id;
	}
	
	public BidFavoriteKeyword setCampaign_id(String campaign_id) {
		this.campaign_id = campaign_id;
		return this;
	}
	
	public String getCampaign_nm() {
		return campaign_nm;
	}
	
	public BidFavoriteKeyword setCampaign_nm(String campaign_nm) {
		this.campaign_nm = campaign_nm;
		return this;
	}
	
	public String getAdgroup_id() {
		return adgroup_id;
	}
	
	public BidFavoriteKeyword setAdgroup_id(String adgroup_id) {
		this.adgroup_id = adgroup_id;
		return this;
	}
	
	public String getAdgroup_nm() {
		return adgroup_nm;
	}
	
	public BidFavoriteKeyword setAdgroup_nm(String adgroup_nm) {
		this.adgroup_nm = adgroup_nm;
		return this;
	}
	
	public String getKwd_id() {
		return kwd_id;
	}
	
	public BidFavoriteKeyword setKwd_id(String kwd_id) {
		this.kwd_id = kwd_id;
		return this;
	}
	
	public String getKwd_nm() {
		return kwd_nm;
	}
	
	public BidFavoriteKeyword setKwd_nm(String kwd_nm) {
		this.kwd_nm = kwd_nm;
		return this;
	}
	
	public Integer getBid_favorites_id() {
		return bid_favorites_id;
	}
	
	public BidFavoriteKeyword setBid_favorites_id(Integer bid_favorites_id) {
		this.bid_favorites_id = bid_favorites_id;
		return this;
	}
	
	public String getBid_status() {
		return bid_status;
	}
	
	public BidFavoriteKeyword setBid_status(String bid_status) {
		this.bid_status = bid_status;
		return this;
	}
	
	public Integer getGoal_rank() {
		return goal_rank;
	}
	
	public BidFavoriteKeyword setGoal_rank(Integer goal_rank) {
		this.goal_rank = goal_rank;
		return this;
	}
	
	public Integer getMax_bid_amt() {
		return max_bid_amt;
	}
	
	public BidFavoriteKeyword setMax_bid_amt(Integer max_bid_amt) {
		this.max_bid_amt = max_bid_amt;
		return this;
	}
	
	public Integer getRank() {
		return rank;
	}
	
	public BidFavoriteKeyword setRank(Integer rank) {
		this.rank = rank;
		return this;
	}
	
	public Integer getRank_range() {
		return rank_range;
	}
	
	public BidFavoriteKeyword setRank_range(Integer rank_range) {
		this.rank_range = rank_range;
		return this;
	}
	
	public Timestamp getLast_rank_at() {
		return last_rank_at;
	}
	
	public BidFavoriteKeyword setLast_rank_at(Timestamp last_rank_at) {
		this.last_rank_at = last_rank_at;
		return this;
	}
	
	public Integer getCur_bid_amt() {
		return cur_bid_amt;
	}
	
	public BidFavoriteKeyword setCur_bid_amt(Integer cur_bid_amt) {
		this.cur_bid_amt = cur_bid_amt;
		return this;
	}
	
	public Integer getBefore_bid_amt() {
		return before_bid_amt;
	}
	
	public BidFavoriteKeyword setBefore_bid_amt(Integer before_bid_amt) {
		this.before_bid_amt = before_bid_amt;
		return this;
	}
	
	public Timestamp getBid_updated_at() {
		return bid_updated_at;
	}
	
	public BidFavoriteKeyword setBid_updated_at(Timestamp bid_updated_at) {
		this.bid_updated_at = bid_updated_at;
		return this;
	}
	
	public Timestamp getCreated_at() {
		return created_at;
	}
	
	public BidFavoriteKeyword setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
		return this;
	}
	
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	
	public BidFavoriteKeyword setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
		return this;
	}
	
	public String getSite() {
		return site;
	}
	
	public BidFavoriteKeyword setSite(String site) {
		this.site = site;
		return this;
	}
	
	public String getTarget() {
		return target;
	}
	
	public BidFavoriteKeyword setTarget(String target) {
		this.target = target;
		return this;
	}
	
	public Integer getProcess_num() {
		return process_num;
	}
	
	public BidFavoriteKeyword setProcess_num(Integer process_num) {
		this.process_num = process_num;
		return this;
	}
	
	public String getEmergency_status() {
		return emergency_status;
	}
	
	public BidFavoriteKeyword setEmergency_status(String emergency_status) {
		this.emergency_status = emergency_status;
		return this;
	}
	
	public String getBid_cpa_status() {
		return bid_cpa_status;
	}
	
	public BidFavoriteKeyword setBid_cpa_status(String bid_cpa_status) {
		this.bid_cpa_status = bid_cpa_status;
		return this;
	}
	
	public Integer getCur_cpa_amt() {
		return cur_cpa_amt;
	}
	
	public BidFavoriteKeyword setCur_cpa_amt(Integer cur_cpa_amt) {
		this.cur_cpa_amt = cur_cpa_amt;
		return this;
	}
	
	public Integer getGoal_cpa_amt() {
		return goal_cpa_amt;
	}
	
	public BidFavoriteKeyword setGoal_cpa_amt(Integer goal_cpa_amt) {
		this.goal_cpa_amt = goal_cpa_amt;
		return this;
	}
	
	public Integer getRec_clk_rnk() {
		return rec_clk_rnk;
	}
	
	public BidFavoriteKeyword setRec_clk_rnk(Integer rec_clk_rnk) {
		this.rec_clk_rnk = rec_clk_rnk;
		return this;
	}
	
	public Timestamp getRec_clk_at() {
		return rec_clk_at;
	}
	
	public BidFavoriteKeyword setRec_clk_at(Timestamp rec_clk_at) {
		this.rec_clk_at = rec_clk_at;
		return this;
	}
	
	public Integer getCur_opp_rank() {
		return cur_opp_rank;
	}
	
	public BidFavoriteKeyword setCur_opp_rank(Integer cur_opp_rank) {
		this.cur_opp_rank = cur_opp_rank;
		return this;
	}
	
	public String getOpp_site() {
		return opp_site;
	}
	
	public BidFavoriteKeyword setOpp_site(String opp_site) {
		this.opp_site = opp_site;
		return this;
	}
	
	public Integer getOpp_gap() {
		return opp_gap;
	}
	
	public BidFavoriteKeyword setOpp_gap(Integer opp_gap) {
		this.opp_gap = opp_gap;
		return this;
	}
	
	public String getResv_status() {
		return resv_status;
	}
	
	public BidFavoriteKeyword setResv_status(String resv_status) {
		this.resv_status = resv_status;
		return this;
	}
	
	public String getMsg_cd() {
		return msg_cd;
	}
	
	public BidFavoriteKeyword setMsg_cd(String msg_cd) {
		this.msg_cd = msg_cd;
		return this;
	}
	
	public String getIs_resv() {
		return is_resv;
	}
	
	public BidFavoriteKeyword setIs_resv(String is_resv) {
		this.is_resv = is_resv;
		return this;
	}
	
	public Integer getMin_bid_amt() {
		return min_bid_amt;
	}
	
	public BidFavoriteKeyword setMin_bid_amt(Integer min_bid_amt) {
		this.min_bid_amt = min_bid_amt;
		return this;
	}
	
	public Integer getOpp_goal_rank() {
		return opp_goal_rank;
	}
	
	public BidFavoriteKeyword setOpp_goal_rank(Integer opp_goal_rank) {
		this.opp_goal_rank = opp_goal_rank;
		return this;
	}
	
	public Double getBid_success_rate() {
		return bid_success_rate;
	}
	
	public BidFavoriteKeyword setBid_success_rate(Double bid_success_rate) {
		this.bid_success_rate = bid_success_rate;
		return this;
	}

	@Override
	public String toString() {
		return "BidFavoriteKeyword [id=" + id + ", created_by=" + created_by + ", updated_by=" + updated_by
				+ ", ad_med_id=" + ad_med_id + ", campaign_id=" + campaign_id + ", campaign_nm=" + campaign_nm
				+ ", adgroup_id=" + adgroup_id + ", adgroup_nm=" + adgroup_nm + ", kwd_id=" + kwd_id + ", kwd_nm="
				+ kwd_nm + ", bid_favorites_id=" + bid_favorites_id + ", bid_status=" + bid_status + ", goal_rank="
				+ goal_rank + ", max_bid_amt=" + max_bid_amt + ", rank=" + rank + ", rank_range=" + rank_range
				+ ", last_rank_at=" + last_rank_at + ", cur_bid_amt=" + cur_bid_amt + ", before_bid_amt="
				+ before_bid_amt + ", bid_updated_at=" + bid_updated_at + ", created_at=" + created_at + ", updated_at="
				+ updated_at + ", site=" + site + ", target=" + target + ", process_num=" + process_num
				+ ", emergency_status=" + emergency_status + ", adv_id=" + adv_id + ", na_account_ser=" + na_account_ser
				+ ", is_resv=" + is_resv + ", bid_cpa_status=" + bid_cpa_status + ", cur_cpa_amt=" + cur_cpa_amt
				+ ", goal_cpa_amt=" + goal_cpa_amt + ", rec_clk_rnk=" + rec_clk_rnk + ", rec_clk_at=" + rec_clk_at
				+ ", cur_opp_rank=" + cur_opp_rank + ", opp_site=" + opp_site + ", opp_gap=" + opp_gap
				+ ", resv_status=" + resv_status + ", msg_cd=" + msg_cd + ", min_bid_amt=" + min_bid_amt
				+ ", opp_goal_rank=" + opp_goal_rank + ", bid_success_rate=" + bid_success_rate + "]";
	}
}
