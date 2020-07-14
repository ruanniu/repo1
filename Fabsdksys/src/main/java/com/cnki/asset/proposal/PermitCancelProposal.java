package com.cnki.asset.proposal;

import com.cnki.asset.domain.TransactionRights;

//权利许可到期/撤销
public class PermitCancelProposal {
	private String trtype;//交易类型（许可到期/撤销）
	private String pkri;//确权标识	
	private String fpkrai;//发布资产标识
	private String tpkrai;//获取资产标识	 		
	private String certificate; //证明文件：交易的证明文件，例如合同复印件等
		
	public PermitCancelProposal() {
		
	}
	
	public PermitCancelProposal(String trtype, String pkri, String fpkrai, String tpkrai, String certificate) {		
		this.trtype = trtype;
		this.pkri = pkri;
		this.fpkrai = fpkrai;
		this.tpkrai = tpkrai;
		this.certificate = certificate;
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
	public String getFpkrai() {
		return fpkrai;
	}
	public void setFpkrai(String fpkrai) {
		this.fpkrai = fpkrai;
	}
	public String getTpkrai() {
		return tpkrai;
	}
	public void setTpkrai(String tpkrai) {
		this.tpkrai = tpkrai;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	
	
}
