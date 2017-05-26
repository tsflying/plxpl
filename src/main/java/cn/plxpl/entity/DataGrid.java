package cn.plxpl.entity;

import net.sf.json.JSONArray;

public class DataGrid {
	private JSONArray rows;
	private int total;

	public JSONArray getRows() {
		return rows;
	}

	public void setRows(JSONArray rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
