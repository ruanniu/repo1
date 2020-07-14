package com.cnki.asset.proposal;

import com.cnki.asset.domain.Ownerrights;

//资产登记撤销协议
public class CancelProposal {
	private String pkrai;//资产标识	 
	 private String trtype;//交易类型（登记撤销）
	 private String pkri;//确权标识（PKRI）		 
	 private String certificate; //证明文件
	 
	 
	public CancelProposal() {
	
	}
	
	public CancelProposal(String pkrai, String trtype, String pkri, String certificate) {		
		this.pkrai = pkrai;
		this.trtype = trtype;
		this.pkri = pkri;
		this.certificate = certificate;
	}

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
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
		 
}
