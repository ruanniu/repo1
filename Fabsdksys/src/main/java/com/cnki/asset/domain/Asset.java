package com.cnki.asset.domain;

import java.util.List;

//资产数据结构：对应区块链世界状态的KV库保存的对象
public class Asset {
	 private String docType;//对象类型
     
	 private String pkrai;//专业知识资源资产标识
	 
	 private String trType;//交易类型
	 private String pkri;//专业知识资源确权标识（PKRI）	
	 private Ownerrights ownerrights;//拥有者权利描述	 
	 private String state; //状态
	 
	 private List<String> permits; //许可集

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getPkrai() {
		return pkrai;
	}

	public void setPkrai(String pkrai) {
		this.pkrai = pkrai;
	}

	public String getTrType() {
		return trType;
	}

	public void setTrType(String trType) {
		this.trType = trType;
	}

	public String getPkri() {
		return pkri;
	}

	public void setPkri(String pkri) {
		this.pkri = pkri;
	}

	

	public Ownerrights getOwnerrights() {
		return ownerrights;
	}

	public void setOwnerrights(Ownerrights ownerrights) {
		this.ownerrights = ownerrights;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<String> getPermits() {
		return permits;
	}

	public void setPermits(List<String> permits) {
		this.permits = permits;
	}
	 	 
}
