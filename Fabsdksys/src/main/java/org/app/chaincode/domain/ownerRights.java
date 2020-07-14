package org.app.chaincode.domain;

//拥有者权利
public class ownerRights {
	private String Pkrai;
	private String Version;
	private pkr Pkr;
	private owner Owner; 
	private rights Rights;
    private String TimeStamp;
    private String Signature;
	public String getPkrai() {
		return Pkrai;
	}
	public void setPkrai(String pkrai) {
		Pkrai = pkrai;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public pkr getPkr() {
		return Pkr;
	}
	public void setPkr(pkr pkr) {
		Pkr = pkr;
	}
	public owner getOwner() {
		return Owner;
	}
	public void setOwner(owner owner) {
		Owner = owner;
	}
	public rights getRights() {
		return Rights;
	}
	public void setRights(rights rights) {
		Rights = rights;
	}
	public String getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}
	public String getSignature() {
		return Signature;
	}
	public void setSignature(String signature) {
		Signature = signature;
	}
    
    
}
