package com.wz.bean.bean;

import java.io.Serializable;

/**
 * 分页处理基类
 * 
 * @author Johnson.Jia
 */
public class Page implements Serializable {

	/**
	 * @author Johnson.Jia
	 */
	private static final long serialVersionUID = 7793805782320693908L;

	private int startIndex;

	private int pn = 1;

	private int pSize = 20;

	private int pageTotal;

	public Page() {
		this(1, 20);
	}

	public Page(int pn, int pSize) {
		super();
		this.pn = pn;
		this.pSize = pSize;
	}

	public int getpSize() {
		return pSize;
	}

	public void setpSize(int pSize) {
		if (pSize <= 0) {
			pSize = 20;
		}
		this.pSize = pSize;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getPn() {
		return pn;
	}

	public void setPn(int pn) {
		if (pn <= 0) {
			pn = 1;
		}
		this.pn = pn;
	}

	public int getStartIndex() {
		if (startIndex <= 0) {
			startIndex = (pn - 1) * pSize;
		}
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

}
