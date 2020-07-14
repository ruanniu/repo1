package com.cnki.asset.domain;

//交易主体:描述交易双方，包括权利发布者和权利获得者。交易方是机构的，采用统一社会信用代码；
//交易方是个人的，采用身份证号码，或由专业知识资源资产管理系统指定
public class Party {
     private Assigner assignor;//权利发布者实体
     private Assigner assignee;//权利获得者（Assignee）实体
	public Assigner getAssignor() {
		return assignor;
	}
	public void setAssignor(Assigner assignor) {
		this.assignor = assignor;
	}
	public Assigner getAssignee() {
		return assignee;
	}
	public void setAssignee(Assigner assignee) {
		this.assignee = assignee;
	}
     
     
}
