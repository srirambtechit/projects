package com.techgig.commzoneproblem;

import java.util.Arrays;
import java.util.List;

public enum Placement {

    //@formatter:off
    TOP_LEFT_CORNER(Arrays.asList(new Direction[]{Direction.RIGHT, Direction.DOWN_RIGHT_DIAGONAL, Direction.DOWN})), 
    BOTTOM_LEFT_CORNER(Arrays.asList(new Direction[]{Direction.UP, Direction.UP_RIGHT_DIAGONAL, Direction.RIGHT})), 
    MID_LEFT_CORNER(Arrays.asList(new Direction[]{Direction.UP, Direction.DOWN, Direction.UP_RIGHT_DIAGONAL, Direction.RIGHT, Direction.DOWN_RIGHT_DIAGONAL})),
    
    TOP_RIGHT_CORNER(Arrays.asList(new Direction[]{Direction.LEFT, Direction.DOWN, Direction.DOWN_LEFT_DIAGONAL})), 
    BOTTOM_RIGHT_CORNER(Arrays.asList(new Direction[]{Direction.UP, Direction.LEFT, Direction.UP_LEFT_DIAGONAL})), 
    MID_RIGHT_CORNER(Arrays.asList(new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.UP_LEFT_DIAGONAL, Direction.DOWN_LEFT_DIAGONAL})),
    
    TOP_MID_CORNER(Arrays.asList(new Direction[]{Direction.LEFT, Direction.RIGHT, Direction.DOWN, Direction.DOWN_LEFT_DIAGONAL, Direction.DOWN_RIGHT_DIAGONAL})), 
    BOTTOM_MID_CORNER(Arrays.asList(new Direction[]{Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.UP_LEFT_DIAGONAL, Direction.UP_RIGHT_DIAGONAL})), 
    CENTER(Arrays.asList(new Direction[]{Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN_LEFT_DIAGONAL, Direction.DOWN_RIGHT_DIAGONAL, Direction.UP_LEFT_DIAGONAL, Direction.UP_RIGHT_DIAGONAL}));
    //@formatter:on

    private List<Direction> directions;

    Placement(List<Direction> directions) {
	this.setDirections(directions);
    }

    public List<Direction> getDirections() {
	return directions;
    }

    private void setDirections(List<Direction> directions) {
	this.directions = directions;
    }

    /**
     * @param x
     * @param y
     * @param xLimit
     * @param yLimit
     * @return
     */
    public static Placement getPlacement(int x, int y, int xLimit, int yLimit) {
	xLimit--;
	yLimit--;
	// Corner cell
	if (x == 0 && y == 0) {
	    return TOP_LEFT_CORNER;
	} else if (x == 0 && y == yLimit) {
	    return TOP_RIGHT_CORNER;
	} else if (x == xLimit && y == 0) {
	    return BOTTOM_LEFT_CORNER;
	} else if (x == xLimit && y == yLimit) {
	    return BOTTOM_RIGHT_CORNER;
	}
	// Middle cell
	else if (x > 0 && x < xLimit) {
	    if (y == 0) {
		return MID_LEFT_CORNER;
	    } else if (y == yLimit) {
		return MID_RIGHT_CORNER;
	    }
	} else if (y > 0 && y < yLimit) {
	    if (x == 0) {
		return TOP_MID_CORNER;
	    } else if (x == xLimit) {
		return BOTTOM_MID_CORNER;
	    }
	}
	return CENTER;
    }

}
