package com.cnki.asset.domain;

//约束（Constraint）：描述对所有权利项的约束
public class Constraint {
	//约束权利使用的日期时间范围
	private String start;
	private String end;
	
	private String region;//约束权利的有效使用地域范围

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	
}
