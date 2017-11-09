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
				+ ", emergency_status=" + emergency_status + "]";
	}
}
