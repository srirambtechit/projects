package com.techgig.travelproblem;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private Element value;
    private int x;
    private int y;
    private GridPanel panel;

    public Cell(GridPanel panel, Element value, int x, int y) {
	super();
	this.panel = panel;
	this.value = value;
	this.x = x;
	this.y = y;
    }

    public boolean isLastCell() {
	return (x + 1) == panel.getRow() && (y + 1) == panel.getCol();
    }

    public boolean hasZero() {
	return value == Element.ZERO;
    }

    public List<Cell> getNeighbourElements() {
	int row = panel.getRow();
	int col = panel.getCol();

	List<Cell> elements = new ArrayList<>();
	for (Direction direction : value.movements) {
	    int nextElementX = x;
	    int nextElementY = y;
	    switch (direction) {
	    case DIAGONAL:
		nextElementX++;
		nextElementY++;
		break;
	    case LOWER:
		nextElementX++;
		break;
	    case RIGHT:
		nextElementY++;
		break;
	    case NO_MOVE:
		break;
	    }
	    if (nextElementX < row && nextElementY < col) {
		elements.add(panel.getCell(nextElementX, nextElementY));
	    }
	}
	return elements;
    }

    public String toString() {
	return String.format("{%s %d, %d}", value.toString(), x, y);
    }

}
