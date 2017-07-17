package com.msrm.dbutil;

import java.util.ArrayList;
import java.util.List;

public class SqlResult {

	private List<String> columns;
	private List<List<String>> rows;
	private String errorJson;

	public String getErrorJson() {
		return errorJson;
	}

	public void setErrorJson(String errorJson) {
		this.errorJson = errorJson;
	}

	public List<String> getColumns() {
		if (columns == null)
			this.columns = new ArrayList<>();
		return columns;
	}

	public List<List<String>> getRows() {
		if (rows == null)
			this.rows = new ArrayList<>();
		return rows;
	}

	@Override
	public String toString() {
		return "SqlResult [columns=" + columns + ", rows=" + rows + ", errorJson=" + errorJson + "]";
	}

}
