package com.cnki.asset.domain;
//拥有者权利：对应账本的"拥有者权利描述"
public class Ownerrights {
   private String pkrai;//专业知识资源资产标识
   private String version;//版本
   
   private Pkr pkr; //专业知识资源
   private Assigner owner; //资源拥有者
   private Rights rights;//拥有者拥有的权利   
   private String timeStamp;//时间戳
   
   private String signature; //数字签名，先用字符串代替，后续根据实际更改

	public String getPkrai() {
		return pkrai;
	}
	
	public void setPkrai(String pkrai) {
		this.pkrai = pkrai;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Pkr getPkr() {
		return pkr;
	}
	
	public void setPkr(Pkr pkr) {
		this.pkr = pkr;
	}
		
	public Assigner getOwner() {
		return owner;
	}

	public void setOwner(Assigner owner) {
		this.owner = owner;
	}

	public Rights getRights() {
		return rights;
	}
	
	public void setRights(Rights rights) {
		this.rights = rights;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
      
}
