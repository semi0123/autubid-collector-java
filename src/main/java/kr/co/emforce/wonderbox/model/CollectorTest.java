package kr.co.emforce.wonderbox.model;

import java.sql.Timestamp;

public class CollectorTest {
	private Integer id;
	private String name;
	private Timestamp created_at;
	private Timestamp updated_at;
	
	public Integer getId() {
		return id;
	}
	public CollectorTest setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public CollectorTest setName(String name) {
		this.name = name;
		return this;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public CollectorTest setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
		return this;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public CollectorTest setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
		return this;
	}
	@Override
	public String toString() {
		return "CollectorTest [id=" + id + ", name=" + name + ", created_at=" + created_at + ", updated_at="
				+ updated_at + "]";
	}
	
}
