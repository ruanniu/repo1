package com.cnki.asset.domain;

import java.util.List;

//描述拥有者拥有的权利
public class Rights {
	private Constraint constraint;  //权利项的约束
	private List<Rightitem> rightitems;//权利项列表
	
	public Constraint getConstraint() {
		return constraint;
	}
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}
	public List<Rightitem> getRightitems() {
		return rightitems;
	}
	public void setRightitems(List<Rightitem> rightitems) {
		this.rightitems = rightitems;
	}
	
	
}
