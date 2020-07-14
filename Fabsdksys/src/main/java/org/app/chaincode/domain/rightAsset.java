package org.app.chaincode.domain;

public class rightAsset {

	private String ObjectType; //在状态数据库用来区分对象类型（值为asset）
	private String AssetId;     //key:资产ID
	private String Trtype;      //交易类型
	private String Flag;    //确权标识

	private ownerRights OwnerRights;            //拥有者权利描
	private String Permits;   //许可集
	private String State; //状态
	public String getObjectType() {
		return ObjectType;
	}
	public void setObjectType(String objectType) {
		ObjectType = objectType;
	}
	public String getAssetId() {
		return AssetId;
	}
	public void setAssetId(String assetId) {
		AssetId = assetId;
	}
	public String getTrtype() {
		return Trtype;
	}
	public void setTrtype(String trtype) {
		Trtype = trtype;
	}
	public String getFlag() {
		return Flag;
	}
	public void setFlag(String flag) {
		Flag = flag;
	}
	public ownerRights getOwnerRights() {
		return OwnerRights;
	}
	public void setOwnerRights(ownerRights ownerRights) {
		OwnerRights = ownerRights;
	}
	public String getPermits() {
		return Permits;
	}
	public void setPermits(String permits) {
		Permits = permits;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	
	
}
