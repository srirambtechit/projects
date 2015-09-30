package com.techgig.commzoneproblem;

import java.util.ArrayList;
import java.util.List;

public class City {

    private int id;

    private int latitude; // x - coordinate

    private int longitude; // y - coordinate

    private int cost;

    private boolean commEstablished;

    private Placement placement;

    private State state;

    public City(State state, int latitude, int longitude, int cost) {
	this.state = state;
	this.latitude = latitude;
	this.longitude = longitude;
	this.cost = cost;
	enableCommunicationByCost();
    }

    private void enableCommunicationByCost() {
	if (cost == -1) {
	    setCommEstablished(true);
	}
    }

    public List<City> getNeighbours() {
	return getNeighbours(placement.getDirections());
    }

    private List<City> getNeighbours(List<Direction> directions) {
	List<City> cityList = new ArrayList<>();
	for (Direction direction : directions) {
	    int x = latitude;
	    int y = longitude;
	    switch (direction) {
	    case UP:
		x--;
		break;
	    case DOWN:
		x++;
		break;
	    case LEFT:
		y--;
		break;
	    case RIGHT:
		y++;
		break;
	    case DOWN_LEFT_DIAGONAL:
		x++;
		y--;
		break;
	    case DOWN_RIGHT_DIAGONAL:
		x++;
		y++;
		break;
	    case UP_LEFT_DIAGONAL:
		x--;
		y--;
		break;
	    case UP_RIGHT_DIAGONAL:
		x--;
		y++;
		break;
	    }
	    cityList.add(state.getCities()[x][y]); // 435294142 - 6831
	}
	System.out.println("cityList : " + cityList);
	return cityList;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public boolean isCommEstablished() {
	return commEstablished;
    }

    public void setCommEstablished(boolean commEstablished) {
	this.commEstablished = commEstablished;
    }

    public Placement getPlacement() {
	return placement;
    }

    public void setPlacement(Placement placement) {
	this.placement = placement;
    }

    @Override
    public String toString() {
	return "City [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", cost=" + cost + ", commEstablished=" + commEstablished + ", placement=" + placement + "]";
    }

}
