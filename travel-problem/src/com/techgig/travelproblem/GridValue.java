package com.techgig.travelproblem;

public enum GridValue {
    //@formatter:off
    ZERO(0, new Direction[] { Direction.NO_MOVE }), 
    ONE(1, new Direction[] { Direction.RIGHT }), 
    TWO(2, new Direction[] { Direction.LOWER}), 
    THREE(3, new Direction[] { Direction.DIAGONAL}), 
    FOUR(4, new Direction[] { Direction.RIGHT, Direction.LOWER }), 
    FIVE(5, new Direction[] { Direction.RIGHT, Direction.DIAGONAL }), 
    SIX(6, new Direction[] { Direction.LOWER, Direction.DIAGONAL }), 
    SEVEN(7, new Direction[] { Direction.RIGHT, Direction.LOWER, Direction.DIAGONAL });
    //@formatter:on

    int value;
    public Direction[] movements;

    GridValue(int value, Direction[] movements) {
	this.value = value;
	this.movements = movements;
    }

    public static GridValue getEnum(int value) {
	//@formatter:off
	switch (value) {
    	case 1: return ONE;
    	case 2: return TWO;
    	case 3: return THREE;
    	case 4: return FOUR;
    	case 5: return FIVE;
    	case 6: return SIX;
    	case 7: return SEVEN;
    	default: return ZERO;
	}
    }
    
    public String toString() {
	return String.format("[%d]", value);
    }

}
