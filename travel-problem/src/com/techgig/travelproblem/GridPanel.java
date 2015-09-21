package com.techgig.travelproblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridPanel {
    private int row;
    private int col;
    private Cell[][] cells;

    public GridPanel(int[] dimension, int[] values) {
	validate(dimension, values);
	createPanel(dimension);
	loadDataOnPanel(values);
    }

    public int getRow() {
	return row;
    }

    public int getCol() {
	return col;
    }

    public Cell getCell(int x, int y) {
	return cells[x][y];
    }

    private void loadDataOnPanel(int[] values) {
	int index = 0;
	for (int i = 0; i < row; i++) {
	    for (int j = 0; j < col; j++) {
		cells[i][j] = new Cell(this, Element.getElementByValue(values[index++]), i, j);
	    }
	}
    }

    private void createPanel(int[] dimension) {
	this.row = dimension[0];
	this.col = dimension[1];
	cells = new Cell[row][col];
    }

    private void validate(int[] dimension, int[] values) {
	if (dimension == null || dimension.length != 2) {
	    throw new IllegalArgumentException("Invalid rows/columns");
	}
	if (dimension[0] <= 0 || dimension[1] <= 0) {
	    throw new IllegalArgumentException("Invalid rows/columns");
	}
	if (values == null || (dimension[0] * dimension[1]) != values.length) {
	    throw new IllegalArgumentException("Invalid input values");
	}
	// since grid values cloud be in the range of 0 to 7
	for (int i : values) {
	    if (i > 7 || i < 0) {
		throw new IllegalArgumentException("Invalid input values");
	    }
	}
    }

    public int findTotalPaths() {
	int totalPaths = 0;

	// Map object to hold current cell as key and neighbour cells as value
	Map<Cell, List<Cell>> map = new HashMap<>();

	// Assumes that, elements starts at top left corner of the GridPanel
	Cell cell = cells[0][0];
	map.put(cell, cell.getNeighbourElements());
	while (!map.isEmpty()) {
	    
	}
	return totalPaths;
    }

    public String toString() {
	StringBuffer buf = new StringBuffer();
	for (int i = 0; i < row; i++) {
	    for (int j = 0; j < col; j++) {
		buf.append(this.cells[i][j]);
	    }
	    buf.append("\n");
	}
	return buf.toString();
    }

}
