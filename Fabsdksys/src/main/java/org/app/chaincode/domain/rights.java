package org.app.chaincode.domain;

import java.util.List;

//资产权利
public class rights {
	private String Constraint;
	private List<String> RightItems;
	
	public String getConstraint() {
		return Constraint;
	}
	public void setConstraint(String constraint) {
		Constraint = constraint;
	}
	public List<String> getRightItems() {
		return RightItems;
	}
	public void setRightItems(List<String> rightItems) {
		RightItems = rightItems;
	}
		
}
