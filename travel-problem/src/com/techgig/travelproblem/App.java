package com.techgig.travelproblem;

public class App {

    public static void main(String[] args) {
	int[] dim = { 4, 6 };
	int[] gridValues = { 1, 3, 0, 0, 0, 0, 0, 0, 4, 5, 1, 0, 0, 0, 0, 6, 7, 6, 0, 0, 0, 0, 5, 0 };

	GridPanel panel = new GridPanel(dim, gridValues);
	System.out.println(panel);
	int paths = panel.findTotalPaths();
	System.out.println("Total no.of paths : " + paths);

	for (int x = 0; x < panel.getRow(); x++) {
	    for (int y = 0; y < panel.getCol(); y++) {
		System.out.printf("%s : %s%n", panel.getCell(x, y), panel.getCell(x, y).getNeighbourElements());
	    }
	}

	System.out.println("paths : " + panel.findPaths());
    }

}
