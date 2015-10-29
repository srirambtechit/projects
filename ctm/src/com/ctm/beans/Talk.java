package com.ctm.beans;

public class Talk {

    private int id;
    private String topic;
    private int duration;
    private static int newId = 1;

    public Talk(String topic, int duration) {
	super();
	this.id = newId++;
	this.topic = topic;
	this.duration = duration;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getTopic() {
	return topic;
    }

    public void setTopic(String topic) {
	this.topic = topic;
    }

    public int getDuration() {
	return duration;
    }

    public void setDuration(int duration) {
	this.duration = duration;
    }

    @Override
    public String toString() {
	return "Talk [id=" + id + ", topic=" + topic + ", duration=" + duration + "]";
    }

}
