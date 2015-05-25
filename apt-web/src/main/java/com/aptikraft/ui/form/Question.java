package com.aptikraft.ui.form;

import java.util.List;

public class Question {

	private int id;

	private String question;

	private String answer;

	private List<String> choices;

	public Question() {

	}

	public Question(int id, String question, String answer, List<String> choices) {
		super();
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.choices = choices;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

}
