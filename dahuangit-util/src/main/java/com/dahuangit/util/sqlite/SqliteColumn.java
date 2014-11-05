package com.dahuangit.util.sqlite;

/**
 * sqlite列对象
 * 
 * @author 黄仁良
 * 
 */
public class SqliteColumn {
	/**
	 * 列名
	 */
	private String columnName;
	/**
	 * 长度
	 */
	private int length;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
