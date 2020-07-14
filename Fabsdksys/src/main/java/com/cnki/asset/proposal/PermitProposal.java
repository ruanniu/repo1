package com.cnki.asset.proposal;

import com.cnki.asset.domain.TransactionRights;

//权利许可交易议案：交易类型（许可）、确权标识、发布资产标识、获取资产标识、交易权利描述、[证明文件]
public class PermitProposal {
	
	private String trtype;//交易类型（许可）
	private String pkri;//确定这次许可交易的标识:可从交易权利描述中获取
	
	private String fpkrai;//发布资产标识：指明哪个资产要提供许可
	private String tpkrai;//获取资产标识：新生成的资产标识
	 	
	//交易权利描述	：目的：一个是 在区块链上记录交易的内容，另外可以从里面提取信息构建拥有者权利描述OwnerRights
	//方便链码写入新的资产对象asset
	private TransactionRights transactionRights;
	private String certificate; //证明文件：交易的证明文件，例如合同复印件等
	
	
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
	public TransactionRights getTransactionRights() {
		return transactionRights;
	}
	public void setTransactionRights(TransactionRights transactionRights) {
		this.transactionRights = transactionRights;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	 	 
}
