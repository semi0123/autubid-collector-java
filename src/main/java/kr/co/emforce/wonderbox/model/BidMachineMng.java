package kr.co.emforce.wonderbox.model;

import java.sql.Timestamp;

public class BidMachineMng {
	private Integer id;
	private String created_by;
	private String updated_by;
	private Integer process_num;
	private String status;
	private Timestamp cur_crashed_at;
	private Timestamp cur_rerunned_at;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Timestamp checked_at;
	private String ip_v4;
	private String name;
	private String desc;
	
	public String getIp_v4() {
		return ip_v4;
	}
	public BidMachineMng setIp_v4(String ip_v4) {
		this.ip_v4 = ip_v4;
		return this;
	}
	public String getName() {
		return name;
	}
	public BidMachineMng setName(String name) {
		this.name = name;
		return this;
	}
	public String getDesc() {
		return desc;
	}
	public BidMachineMng setDesc(String desc) {
		this.desc = desc;
		return this;
	}
	public Integer getId() {
		return id;
	}
	public BidMachineMng setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getCreated_by() {
		return created_by;
	}
	public BidMachineMng setCreated_by(String created_by) {
		this.created_by = created_by;
		return this;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public BidMachineMng setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
		return this;
	}
	public Integer getProcess_num() {
		return process_num;
	}
	public BidMachineMng setProcess_num(Integer process_num) {
		this.process_num = process_num;
		return this;
	}
	public String getStatus() {
		return status;
	}
	public BidMachineMng setStatus(String status) {
		this.status = status;
		return this;
	}
	public Timestamp getCur_crashed_at() {
		return cur_crashed_at;
	}
	public BidMachineMng setCur_crashed_at(Timestamp cur_crashed_at) {
		this.cur_crashed_at = cur_crashed_at;
		return this;
	}
	public Timestamp getCur_rerunned_at() {
		return cur_rerunned_at;
	}
	public BidMachineMng setCur_rerunned_at(Timestamp cur_rerunned_at) {
		this.cur_rerunned_at = cur_rerunned_at;
		return this;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public BidMachineMng setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
		return this;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public BidMachineMng setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
		return this;
	}
	public Timestamp getChecked_at() {
		return checked_at;
	}
	public BidMachineMng setChecked_at(Timestamp checked_at) {
		this.checked_at = checked_at;
		return this;
	}
	@Override
	public String toString() {
		return "BidMachineMng [id=" + id + ", created_by=" + created_by + ", updated_by=" + updated_by
				+ ", process_num=" + process_num + ", status=" + status + ", cur_crashed_at=" + cur_crashed_at
				+ ", cur_rerunned_at=" + cur_rerunned_at + ", created_at=" + created_at + ", updated_at=" + updated_at
				+ ", checked_at=" + checked_at + "]";
	}
}
