package com.cnki.asset.domain;

//专业知识资源
public class Pkr {
	private String id;//专业知识资源标识（id）
	private String title;//题名（Title）：描述专业知识资源的名称
	
	private String fingerPrint;//指纹：用于关联资源数据与资源标识

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFingerPrint() {
		return fingerPrint;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}
	
	
}
