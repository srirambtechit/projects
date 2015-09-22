package com.techgig.travelproblem.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.techgig.travelproblem.GridPanel;

public class AppTest {

    int[] dim = { 4, 6 };

    @Test
    public void testCaseOne() {
	int[] gridValues = { 1, 3, 0, 0, 0, 0, 0, 0, 4, 5, 1, 0, 0, 0, 0, 6, 5, 6, 0, 0, 0, 0, 5, 0 };
	GridPanel panel = new GridPanel(dim, gridValues);
	int paths = panel.findPaths();
	assertEquals("TestCase - 1", 2, paths);
    }

    @Test
    public void testCaseTwo() {
	int[] gridValues = { 1, 3, 0, 0, 0, 0, 0, 0, 4, 5, 1, 0, 0, 0, 0, 6, 3, 6, 0, 0, 0, 0, 5, 0 };
	GridPanel panel = new GridPanel(dim, gridValues);
	int paths = panel.findPaths();
	assertEquals("TestCase - 2", 1, paths);
    }

    @Test
    public void testCaseThree() {
	int[] gridValues = { 1, 3, 0, 0, 0, 0, 0, 0, 4, 5, 1, 0, 0, 0, 0, 6, 7, 6, 0, 0, 0, 0, 5, 0 };
	GridPanel panel = new GridPanel(dim, gridValues);
	int paths = panel.findPaths();
	assertEquals("TestCase - 3", 3, paths);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCaseFour() {
	int[] gridValues = {};
	GridPanel panel = new GridPanel(dim, gridValues);
	int paths = panel.findPaths();
	assertEquals("TestCase - 4", 3, paths);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCaseFive() {
	int[] dim = { 3, 2 };
	int[] gridValues = {};
	GridPanel panel = new GridPanel(dim, gridValues);
	int paths = panel.findPaths();
	assertEquals("TestCase - 5", 3, paths);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCaseSix() {
	int[] dim = { -1, -1 };
	int[] gridValues = {};
	GridPanel panel = new GridPanel(dim, gridValues);
	int paths = panel.findPaths();
	assertEquals("TestCase - 6", 3, paths);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCaseSeven() {
	int[] dim = { 2, 2 };
	int[] gridValues = { 9, 2, 0, 1 };
	GridPanel panel = new GridPanel(dim, gridValues);
	int paths = panel.findPaths();
	assertEquals("TestCase - 7", 3, paths);
    }

    @Test
    public void testCaseEight() {
	int[] dim = { 4, 6 };
	int[] gridValues = { 1, 3, 0, 0, 0, 0, 0, 0, 4, 5, 1, 0, 0, 0, 0, 6, 0, 6, 0, 0, 0, 0, 5, 0 };
	GridPanel panel = new GridPanel(dim, gridValues);
	int paths = panel.findPaths();
	assertEquals("TestCase - 8", 0, paths);
    }

    @Test
    public void testCaseNine() {
	int[] dim = { 5, 5 };
	int[] gridValues = { 3, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 3, 5, 2, 0, 0, 0, 3, 7, 0, 0, 0, 0, 1, 0 };
	GridPanel panel = new GridPanel(dim, gridValues);
	int paths = panel.findPaths();
	assertEquals("TestCase - 9", 5, paths);
    }

    @Test
    public void testCaseTen() {
	int[] dim = { 3, 3 };
	int[] gridValues = { 7, 7, 7, 7, 7, 7, 7, 7, 0 };
	GridPanel panel = new GridPanel(dim, gridValues);
	int paths = panel.findPaths();
	assertEquals("TestCase - 10", 13, paths);
    }

}
