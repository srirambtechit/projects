package com.techgig.commzoneproblem;

public class App {

    public static void main(String[] args) {
	String input = "-1@10@-1#10@2@10#-1@10@-1";
	CommunicationEstablisher establisher = new CommunicationEstablisher(input);
	int cost = establisher.getEstablishmentCost();
	System.out.println("Input  : " + input);
	System.out.println("Output : " + cost);

	State state = establisher.getState();
	City[][] cityList = state.getCities();
	System.out.println(cityList[1][1]);
	System.out.println(cityList[1][1].getNeighbours());
    }

    public void tmp(City[][] cityList) {
	for (City[] cities : cityList) {
	    for (City city : cities) {
		System.out.println(city);
		System.out.println(city.getNeighbours());
	    }
	}
    }
}
