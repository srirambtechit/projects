package com.techgig.commzoneproblem;

import java.util.List;

public class City {

    private int id;

    private int latitude; // x - coordinate

    private int longitude; // y - coordinate

    private int cost;

    private boolean commEstablished;

    private Placement placement;

    private List<City> neighbours;

    public City(int latitude, int longitude, int cost) {
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

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getCost() {
	return cost;
    }

    public void setCost(int cost) {
	this.cost = cost;
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

    public List<City> getNeighbours() {
	return neighbours;
    }

    public void setNeighbours(List<City> neighbours) {
	this.neighbours = neighbours;
    }

    public int getLatitude() {
	return latitude;
    }

    public void setLatitude(int latitude) {
	this.latitude = latitude;
    }

    public int getLongitude() {
	return longitude;
    }

    public void setLongitude(int longitude) {
	this.longitude = longitude;
    }

    @Override
    public String toString() {
	return "City [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", cost=" + cost + ", commEstablished=" + commEstablished + ", placement=" + placement + ", neighbours=" + neighbours + "]";
    }

}
