package com.techgig.commzoneproblem;

public class CommunicationEstablisher {

    private State state;

    private int rows;

    private int columns;

    private int[][] data;

    private int establishmentCost;

    public State getState() {
	return state;
    }

    public CommunicationEstablisher(String inputStr) {
	parseInputAndInitData(inputStr);
	processData();
    }

    private void processData() {
	state = new State(rows, columns, data);
	establishmentCost = state.establishCommunicationZone();
    }

    private void parseInputAndInitData(String string) {
	if (string == null || string.isEmpty()) {
	    throw new IllegalArgumentException("Invalid input");
	}

	int rowCount = 0;
	int dataCount = 0;
	String[] rows = string.split("#");
	if (rows != null) {
	    data = new int[rows.length][];
	    for (String strRow : rows) {
		String[] columns = strRow.split("@");
		if (columns != null) {
		    data[rowCount] = new int[columns.length];
		    int columnIndex = 0;
		    for (String strColumn : columns) {
			data[rowCount][columnIndex++] = Integer.valueOf(strColumn);
			dataCount++;
		    }
		}
		rowCount++;
	    }
	}
	this.rows = rowCount;
	this.columns = dataCount / rowCount;
    }

    public int getEstablishmentCost() {
	return establishmentCost;
    }

}
