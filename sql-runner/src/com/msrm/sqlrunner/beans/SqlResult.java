package com.msrm.sqlrunner.beans;

import java.util.ArrayList;
import java.util.List;

public class SqlResult {

	private List<String> columns;
	private List<List<String>> rows;

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

}
