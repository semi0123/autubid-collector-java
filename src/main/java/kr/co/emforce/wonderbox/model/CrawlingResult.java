package kr.co.emforce.wonderbox.model;

public class CrawlingResult {
	private Integer rank;
	private String ad_dsc;
	private String site;
	private String title;
	
	public Integer getRank() {
		return rank;
	}
	public CrawlingResult setRank(Integer rank) {
		this.rank = rank;
		return this;
	}
	public String getAd_dsc() {
		return ad_dsc;
	}
	public CrawlingResult setAd_dsc(String ad_dsc) {
		this.ad_dsc = ad_dsc;
		return this;
	}
	public String getSite() {
		return site;
	}
	public CrawlingResult setSite(String site) {
		this.site = site;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public CrawlingResult setTitle(String title) {
		this.title = title;
		return this;
	}
	@Override
	public String toString() {
		return "CrawlingResult [rank=" + rank + ", ad_dsc=" + ad_dsc + ", site=" + site + ", title=" + title + "]";
	}
}
