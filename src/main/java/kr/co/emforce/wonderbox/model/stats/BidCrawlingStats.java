package kr.co.emforce.wonderbox.model.stats;

import java.sql.Timestamp;

public class BidCrawlingStats {
	private Integer id;
	private String created_by;
	private String updated_by;
	private String scheduled_at;
	private String server_name;
	private String emergency_status;
	private Integer process_num;
	private Integer kwd_cnt;
	private Integer error_cnt;
	private Integer success_cnt;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Integer work_time;
	public Integer getId() {
		return id;
	}
	public BidCrawlingStats setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getCreated_by() {
		return created_by;
	}
	public BidCrawlingStats setCreated_by(String created_by) {
		this.created_by = created_by;
		return this;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public BidCrawlingStats setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
		return this;
	}
	public String getScheduled_at() {
		return scheduled_at;
	}
	public BidCrawlingStats setScheduled_at(String scheduled_at) {
		this.scheduled_at = scheduled_at;
		return this;
	}
	public String getServer_name() {
		return server_name;
	}
	public BidCrawlingStats setServer_name(String server_name) {
		this.server_name = server_name;
		return this;
	}
	public String getEmergency_status() {
		return emergency_status;
	}
	public BidCrawlingStats setEmergency_status(String emergency_status) {
		this.emergency_status = emergency_status;
		return this;
	}
	public Integer getProcess_num() {
		return process_num;
	}
	public BidCrawlingStats setProcess_num(Integer process_num) {
		this.process_num = process_num;
		return this;
	}
	public Integer getKwd_cnt() {
		return kwd_cnt;
	}
	public BidCrawlingStats setKwd_cnt(Integer kwd_cnt) {
		this.kwd_cnt = kwd_cnt;
		return this;
	}
	public Integer getError_cnt() {
		return error_cnt;
	}
	public BidCrawlingStats setError_cnt(Integer error_cnt) {
		this.error_cnt = error_cnt;
		return this;
	}
	public Integer getSuccess_cnt() {
		return success_cnt;
	}
	public BidCrawlingStats setSuccess_cnt(Integer success_cnt) {
		this.success_cnt = success_cnt;
		return this;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public BidCrawlingStats setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
		return this;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public BidCrawlingStats setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
		return this;
	}
	public Integer getWork_time() {
		return work_time;
	}
	public BidCrawlingStats setWork_time(Integer work_time) {
		this.work_time = work_time;
		return this;
	}
	@Override
	public String toString() {
		return "BidCrawlingStats [id=" + id + ", created_by=" + created_by + ", updated_by=" + updated_by
				+ ", scheduled_at=" + scheduled_at + ", server_name=" + server_name + ", emergency_status="
				+ emergency_status + ", process_num=" + process_num + ", kwd_cnt=" + kwd_cnt + ", error_cnt="
				+ error_cnt + ", success_cnt=" + success_cnt + ", created_at=" + created_at + ", updated_at="
				+ updated_at + ", work_time=" + work_time + "]";
	}
}
