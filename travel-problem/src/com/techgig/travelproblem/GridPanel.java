package com.techgig.travelproblem;

public class GridPanel {
    int row;
    int col;
    GridValue[][] values;

    public GridPanel(int[] dim, int[] values) {
	createPanel(dim);
	assignValues(values);
    }

    private void assignValues(int[] values) {
	if ((row * col) != values.length) {
	    throw new IllegalArgumentException("Invalid values");
	}
	int count = 0;
	for (int i = 0; i < row; i++) {
	    for (int j = 0; j < col; j++) {
		this.values[i][j] = GridValue.getEnum(values[count++]);
	    }
	}
    }

    private void createPanel(int[] dim) {
	if (dim == null || dim.length != 2) {
	    throw new IllegalArgumentException("Invalid dimension");
	}
	this.row = dim[0];
	this.col = dim[1];
	this.values = new GridValue[row][col];
    }

    public String toString() {
	StringBuffer buf = new StringBuffer();
	for (int i = 0; i < row; i++) {
	    for (int j = 0; j < col; j++) {
		buf.append(this.values[i][j]);
	    }
	    buf.append("\n");
	}
	return buf.toString();
    }

    public int findNumberOfPaths() {
	return 0;
    }

}
