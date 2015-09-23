package com.techgig.travelproblem;

public class App {

    public static void main(String[] args) {
	int[] dim = { 3, 3 };
	int[] gridValues = { 7, 7, 7, 7, 7, 7, 7, 7, 0 };
	GridPanel panel = new GridPanel(dim, gridValues);
	System.out.println(panel);

	int paths = panel.findPathCount();
	System.out.println("paths : " + paths);
	
	paths = panel.findPaths();
	System.out.println("paths : " + paths);
    }

}
