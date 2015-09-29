package com.techgig.commzoneproblem;

public class App {

    public static void main(String[] args) {
	String input = "-1@10@-1#10@2@10#-1@10@-1";
	CommunicationEstablisher establisher = new CommunicationEstablisher(input);
	int cost = establisher.getEstablishmentCost();
	System.out.println("Input  : " + input);
	System.out.println("Output : " + cost);
    }

}
