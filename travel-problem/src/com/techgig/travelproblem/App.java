package com.techgig.travelproblem;

public class App {

    public static void main(String[] args) {
	int[] dim = { 4, 6 };
	// int[] gridValues = { 1, 3, 0, 0, 0, 0, 0, 0, 4, 5, 1, 0, 0, 0, 0, 6,
	// 7, 6, 0, 0, 0, 0, 5, 0 };
	int[] gridValues = { 1, 3, 0, 0, 0, 0, 0, 0, 4, 5, 1, 0, 0, 0, 0, 6, 5, 6, 0, 0, 0, 0, 5, 0 };
	// int[] gridValues = { 1, 3, 0, 0, 0, 0, 0, 0, 4, 5, 1, 0, 0, 0, 0, 6,
	// 3, 6, 0, 0, 0, 0, 5, 0 };
	GridPanel panel = new GridPanel(dim, gridValues);
	System.out.println(panel);
	int paths = panel.findPaths();

	System.out.println("paths : " + paths);
    }

}
