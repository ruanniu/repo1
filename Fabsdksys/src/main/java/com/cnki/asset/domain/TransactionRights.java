package com.cnki.asset.domain;

//交易权利描述对象:对应账本的"交易权利描述"
public class TransactionRights {
	private String pkri;//专业知识资源确权标识（PKRI）
	private String version;//版本
	private String transactionType;//交易类型
	
	private Pkr pkr; //专业知识资源
	private Party party;//交易主体	
	private Rights rights;//描述交易的权利 
	
	private String timeStamp;//时间戳	   
	private String signature; //数字签名，先用字符串代替，后续根据实际更改
	public String getPkri() {
		return pkri;
	}
	public void setPkri(String pkri) {
		this.pkri = pkri;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Pkr getPkr() {
		return pkr;
	}
	public void setPkr(Pkr pkr) {
		this.pkr = pkr;
	}
	public Party getParty() {
		return party;
	}
	public void setParty(Party party) {
		this.party = party;
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
