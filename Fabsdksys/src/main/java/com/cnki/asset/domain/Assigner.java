package com.cnki.asset.domain;

//权利发布者（Assignor）实体和权利获得者（Assignee）实体
public class Assigner {
	private String id;//身份标识（id）
	private String name;
	private String contact;
	private String telephone;
	private String address;
	
	
	public Assigner() {		
	}
	
	public Assigner(String id, String name, String contact, String telephone, String address) {		
		this.id = id;
		this.name = name;
		this.contact = contact;
		this.telephone = telephone;
		this.address = address;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
