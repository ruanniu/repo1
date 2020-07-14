package com.cnki.asset.domain;

//权利项（RightItem）：描述该项权利的具体信息
//指明什么权利、权利的约束以及权利的属性
public class Rightitem {
	private int exclusive;//独占性权利:默认为0，可独占；-1非独占
	private int transferable;//可转让性：默认为0，可转让，-1不可转让
	
	private int rightType; //权利类型：固定有13类，指明哪种权利，可取值为复制权、发行权、出租权等。
	private Constraint constraint;//该权利项的约束
	
	public Rightitem() {
		this.exclusive=0;
		this.transferable=0;
	}
	
	public int getExclusive() {
		return exclusive;
	}

	public void setExclusive(int exclusive) {
		this.exclusive = exclusive;
	}

	public int getTransferable() {
		return transferable;
	}

	public void setTransferable(int transferable) {
		this.transferable = transferable;
	}

	public int getRightType() {
		return rightType;
	}

	public void setRightType(int rightType) {
		this.rightType = rightType;
	}

	public Constraint getConstraint() {
		return constraint;
	}
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}
	
	
}
