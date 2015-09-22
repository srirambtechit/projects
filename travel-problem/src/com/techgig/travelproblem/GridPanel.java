package com.techgig.travelproblem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
	Stack<Move> stack = new Stack<>();

	// Assumes that, elements starts at top left corner of the GridPanel
	Move move = new Move();
	move.currentCell = cells[0][0];
	move.neighbours = move.currentCell.getNeighbourElements();
	stack.push(move);

	while (!stack.isEmpty()) {
	    Move currentMove = stack.pop();
	    Cell currentCell = currentMove.currentCell;
	    List<Cell> neighbours = currentMove.neighbours;
	    Cell neighbour = neighbours.remove(0);

	    // dead end found, no more move, resetting currentCell
	    if (neighbour.hasZero()) {
	    }

	    // removing ZEROs from neighbours list
	    Iterator<Cell> iterator = neighbours.iterator();
	    while (iterator.hasNext()) {
		Cell cell = iterator.next();
		if (cell.hasZero()) {
		    iterator.remove();
		}
	    }

	    if (currentCell.isLastCell()) {
		break;
	    }
	}
	return totalPaths;
    }

    public int findTotalPaths0() {
	int totalPaths = 0;

	// Map object to hold current cell as key and neighbour cells as value
	Map<Cell, List<Cell>> map = new HashMap<>();

	// Assumes that, elements starts at top left corner of the GridPanel
	Cell currentCell = cells[0][0];
	map.put(currentCell, currentCell.getNeighbourElements());
	while (!map.isEmpty()) {
	    List<Cell> neighbours = map.get(currentCell);
	    Cell neighbour = neighbours.remove(0);

	    // dead end found, no more move, resetting currentCell
	    if (neighbour.hasZero()) {
		map.remove(currentCell);
	    }

	    // removing ZEROs from neighbours list
	    Iterator<Cell> iterator = neighbours.iterator();
	    while (iterator.hasNext()) {
		Cell cell = iterator.next();
		if (cell.hasZero()) {
		    iterator.remove();
		}
	    }

	    if (neighbours.isEmpty()) {
		map.remove(currentCell);
	    }

	    map.put(neighbour, neighbour.getNeighbourElements());
	    currentCell = neighbour;
	    if (currentCell.isLastCell()) {
		break;
	    }
	}
	return totalPaths;
    }

    private int paths = 0;

    public int findPaths() {
	findPaths(cells[0][0]);
	return paths;
    }

    private void findPaths(Cell currentCell) {
	if (currentCell.isLastCell()) {
	    paths++;
	    return;
	}
	if (currentCell.hasZero()) {
	    return;
	}
	List<Cell> neighbours = currentCell.getNeighbourElements();
	if (neighbours.size() > 1) {
	    Iterator<Cell> itr = neighbours.iterator();
	    while (itr.hasNext()) {
		Cell cell = itr.next();
		itr.remove();
		if (!cell.hasZero()) {
		    findPaths(cell);
		}
	    }
	} else {
	    findPaths(neighbours.get(0));
	}
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

    private class Move {
	Cell currentCell;
	List<Cell> neighbours;
    }

}
