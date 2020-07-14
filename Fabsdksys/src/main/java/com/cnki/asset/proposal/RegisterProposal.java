package com.cnki.asset.proposal;

import com.cnki.asset.domain.Ownerrights;

//资产登记议案
public class RegisterProposal {
	 private String pkrai;//专业知识资源资产标识
	 
	 private String trtype;//交易类型（登记）
	 private String pkri;//专业知识资源确权标识（PKRI）	
	 private Ownerrights ownerrights;//拥有者权利描述	 
	 private String certificate; //证明文件
	
	 public String getPkrai() {
		return pkrai;
	}
	public void setPkrai(String pkrai) {
		this.pkrai = pkrai;
	}
	
	public String getTrtype() {
		return trtype;
	}
	public void setTrtype(String trtype) {
		this.trtype = trtype;
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
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	 	 	 
}
