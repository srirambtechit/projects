package com.ctm.beans;

import java.util.List;

public class Track {

    private int id;
    private List<Talk> talks;
    private String name;

    public Track(int id, List<Talk> talks, String name) {
	super();
	this.id = id;
	this.talks = talks;
	this.name = name;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public List<Talk> getTalks() {
	return talks;
    }

    public void setTalks(List<Talk> talks) {
	this.talks = talks;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return "Track [id=" + id + ", talks=" + talks + ", name=" + name + "]";
    }

}
