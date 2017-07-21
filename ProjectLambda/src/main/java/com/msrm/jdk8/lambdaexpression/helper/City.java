package com.msrm.jdk8.lambdaexpression.helper;

public class City {

	private int id;
	private String name;
	private String state;
	private long population;
	private String populationClass;
	private String cityType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public String getPopulationClass() {
		return populationClass;
	}

	public void setPopulationClass(String populationClass) {
		this.populationClass = populationClass;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", state=" + state + ", population=" + population
				+ ", populationClass=" + populationClass + ", cityType=" + cityType + "]";
	}

}
