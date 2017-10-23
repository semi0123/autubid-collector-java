package kr.co.emforce.wonderbox.model;

public class BidCrawlingStatsRate {
	private String server_name;
	private Integer kwd_cnt;
	private Integer error_cnt;
	private Double error_rate;
	private Integer success_cnt;
	private Double success_rate;
	private Integer work_time;
	private Double avg_time;
	public String getServer_name() {
		return server_name;
	}
	public BidCrawlingStatsRate setServer_name(String server_name) {
		this.server_name = server_name;
		return this;
	}
	public Integer getKwd_cnt() {
		return kwd_cnt;
	}
	public BidCrawlingStatsRate setKwd_cnt(Integer kwd_cnt) {
		this.kwd_cnt = kwd_cnt;
		return this;
	}
	public Integer getError_cnt() {
		return error_cnt;
	}
	public BidCrawlingStatsRate setError_cnt(Integer error_cnt) {
		this.error_cnt = error_cnt;
		return this;
	}
	public Double getError_rate() {
		return error_rate;
	}
	public BidCrawlingStatsRate setError_rate(Double error_rate) {
		this.error_rate = error_rate;
		return this;
	}
	public Integer getSuccess_cnt() {
		return success_cnt;
	}
	public BidCrawlingStatsRate setSuccess_cnt(Integer success_cnt) {
		this.success_cnt = success_cnt;
		return this;
	}
	public Double getSuccess_rate() {
		return success_rate;
	}
	public BidCrawlingStatsRate setSuccess_rate(Double success_rate) {
		this.success_rate = success_rate;
		return this;
	}
	public Integer getWork_time() {
		return work_time;
	}
	public BidCrawlingStatsRate setWork_time(Integer work_time) {
		this.work_time = work_time;
		return this;
	}
	public Double getAvg_time() {
		return avg_time;
	}
	public BidCrawlingStatsRate setAvg_time(Double avg_time) {
		this.avg_time = avg_time;
		return this;
	}
	@Override
	public String toString() {
		return "BidCrawlingStatsRate [server_name=" + server_name + ", kwd_cnt=" + kwd_cnt + ", error_cnt=" + error_cnt
				+ ", error_rate=" + error_rate + ", success_cnt=" + success_cnt + ", success_rate=" + success_rate
				+ ", work_time=" + work_time + ", avg_time=" + avg_time + "]";
	}
}
