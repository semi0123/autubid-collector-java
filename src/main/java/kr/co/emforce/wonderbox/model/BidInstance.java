package kr.co.emforce.wonderbox.model;

public class BidInstance {
	private int id;
	private String created_by;
	private String updated_by;
	private String name;
	private String ip_v4;
	private String status;
	private String desc;
	private String label;
	private String created_at;
	private String updated_at;
	
	public int getId() {
		return id;
	}
	public BidInstance setId(int id) {
		this.id = id;
		return this;
	}
	public String getCreated_by() {
		return created_by;
	}
	public BidInstance setCreated_by(String created_by) {
		this.created_by = created_by;
		return this;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public BidInstance setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
		return this;
	}
	public String getName() {
		return name;
	}
	public BidInstance setName(String name) {
		this.name = name;
		return this;
	}
	public String getIp_v4() {
		return ip_v4;
	}
	public BidInstance setIp_v4(String ip_v4) {
		this.ip_v4 = ip_v4;
		return this;
	}
	public String getStatus() {
		return status;
	}
	public BidInstance setStatus(String status) {
		this.status = status;
		return this;
	}
	public String getDesc() {
		return desc;
	}
	public BidInstance setDesc(String desc) {
		this.desc = desc;
		return this;
	}
	public String getLabel() {
		return label;
	}
	public BidInstance setLabel(String label) {
		this.label = label;
		return this;
	}
	public String getCreated_at() {
		return created_at;
	}
	public BidInstance setCreated_at(String created_at) {
		this.created_at = created_at;
		return this;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public BidInstance setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
		return this;
	}
	@Override
	public String toString() {
		return "BidInstance [id=" + id + ", created_by=" + created_by + ", updated_by=" + updated_by + ", name=" + name
				+ ", ip_v4=" + ip_v4 + ", status=" + status + ", desc=" + desc + ", label=" + label + ", created_at="
				+ created_at + ", updated_at=" + updated_at + "]";
	}
	
	
}
