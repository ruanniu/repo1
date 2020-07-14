package com.cnki.asset.proposal;

import com.cnki.asset.domain.TransactionRights;

//权利转让提案:交易类型、确权标识、发布资产标识、生成资产标识、生成资产标识、交易权利描述、[证明文件317]
public class TransferProposal {
	private String trtype;//交易类型（原始全部转让、原始部分转让、全部再转让、部分再转让）
	private String pkri;//确定这次许可交易的标识:可从交易权利描述中获取
	
	private String fpkrai;//发布资产标识：指明哪个资产要提供许可
	private String tpkrai;//生成资产标识：权利：原始权利项-转让权利项，许可集用原始的
	private String tpkrai1;//生成资产标识：权利：转让的权利项，从交易权利描述获取
	 	
	//交易权利描述	：目的：一个是 在区块链上记录交易的内容，另外可以从里面提取信息构建拥有者权利描述OwnerRights	
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
	public String getTpkrai1() {
		return tpkrai1;
	}
	public void setTpkrai1(String tpkrai1) {
		this.tpkrai1 = tpkrai1;
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
