package com.techgig.travelproblem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class CandidateCode {

    public static void main(String[] args) {
	int[] dim = {};
	int[] gridValues = { 7, 7, 7, 7, 7, 7, 7, 7, 0 };
	System.out.println(CandidateCode.no_of_path(dim, gridValues));
    }

    public static int no_of_path(int[] input1, int[] input2) {
	try {
	    CandidateCode code = new CandidateCode();
	    GridPanel panel = code.new GridPanel(input1, input2);
	    return panel.findPathCount();
	} catch (IllegalArgumentException e) {
	    return 0;
	}
    }

    class GridPanel {
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

	private int paths = 0;

	/**
	 * solution one: recursive with common variable
	 * 
	 * @return
	 */
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
		    findPaths(cell);
		}
	    } else {
		findPaths(neighbours.get(0));
	    }
	}

	/**
	 * solution two: without recursion
	 * 
	 * @return
	 */
	public int findPathCount() {
	    int paths = 0;

	    Cell currentCell = cells[0][0];
	    Stack<Cell> s = new Stack<>();
	    s.add(currentCell);

	    while (!s.isEmpty()) {
		currentCell = s.pop();
		if (currentCell.isLastCell()) {
		    paths++;
		}
		if (currentCell.hasZero()) {
		    continue;
		}
		List<Cell> neighbours = currentCell.getNeighbourElements();
		if (neighbours.size() > 1) {
		    Iterator<Cell> itr = neighbours.iterator();
		    while (itr.hasNext()) {
			Cell cell = itr.next();
			itr.remove();
			s.add(cell);
		    }
		} else {
		    s.add(neighbours.get(0));
		}
	    }

	    return paths;
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

    class Cell {

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
	    return value.toString();
	}

    }

    enum Element {
	ZERO(0, new Direction[] { Direction.NO_MOVE }), ONE(1, new Direction[] { Direction.RIGHT }), TWO(2, new Direction[] { Direction.LOWER }), THREE(3, new Direction[] { Direction.DIAGONAL }), FOUR(4, new Direction[] { Direction.RIGHT, Direction.LOWER }), FIVE(5, new Direction[] { Direction.RIGHT, Direction.DIAGONAL }), SIX(6, new Direction[] { Direction.LOWER, Direction.DIAGONAL }), SEVEN(7, new Direction[] { Direction.RIGHT, Direction.LOWER, Direction.DIAGONAL });

	public int value;
	public Direction[] movements;

	private Element(int value, Direction[] movements) {
	    this.value = value;
	    this.movements = movements;
	}

	public static Element getElementByValue(int value) {
	    switch (value) {
	    case 1:
		return ONE;
	    case 2:
		return TWO;
	    case 3:
		return THREE;
	    case 4:
		return FOUR;
	    case 5:
		return FIVE;
	    case 6:
		return SIX;
	    case 7:
		return SEVEN;
	    default:
		return ZERO;
	    }
	}

	public String toString() {
	    return String.format(" %d ", value);
	}

    }

    enum Direction {
	RIGHT, LOWER, DIAGONAL, NO_MOVE
    }

}