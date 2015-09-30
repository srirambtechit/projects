package com.techgig.commzoneproblem;

public class State {

    private City[][] cities;

    private int latitudeLimit; // boundary of latitude x - axis limit

    private int longitudeLimit; // boundary of longitude y - axis limit

    public State(int rows, int columns, int[][] data) {
	this.setLatitudeLimit(rows);
	this.setLongitudeLimit(columns);
	initCity(rows, columns, data);
    }

    private void initCity(int rows, int columns, int[][] data) {
	// Creating city array object
	this.cities = new City[rows][columns];

	// Filling up data to cities
	int id = 0;
	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < columns; j++) {
		cities[i][j] = new City(this, i, j, data[i][j]);
		cities[i][j].setId(++id);
		cities[i][j].setPlacement(Placement.getPlacement(i, j, rows, columns));
		System.out.println(cities[i][j]);
	    }
	}
	cities[0][0].getNeighbours();
    }

    public City[][] getCities() {
	return cities;
    }

    public int establishCommunicationZone() {
	return 0;
    }

    public int getLatitudeLimit() {
	return latitudeLimit;
    }

    public void setLatitudeLimit(int latitudeLimit) {
	this.latitudeLimit = latitudeLimit;
    }

    public int getLongitudeLimit() {
	return longitudeLimit;
    }

    public void setLongitudeLimit(int longitudeLimit) {
	this.longitudeLimit = longitudeLimit;
    }

}
