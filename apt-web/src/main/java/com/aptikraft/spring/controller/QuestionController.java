package com.aptikraft.spring.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aptikraft.spring.service.ExamService;
import com.aptikraft.spring.service.QuestionService;
import com.aptikraft.spring.view.bean.ExamBO;
import com.aptikraft.spring.view.bean.QuestionBO;
import com.aptikraft.ui.form.Question;
import com.aptikraft.ui.form.QuestionForm;

@Controller
public class QuestionController {

	private ExamService examService;

	private QuestionService questionService;

	@Autowired(required = true)
	@Qualifier(value = "examService")
	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	public ExamService getExamService() {
		return examService;
	}

	@Autowired(required = true)
	@Qualifier(value = "questionService")
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}
	
	@RequestMapping(value = { "/startExamPage" }, method = RequestMethod.GET)
	public @ModelAttribute("questionForm") QuestionForm setupForm() {
		ExamBO examBO = fetchExamDetails();
		List<Question> questions = new ArrayList<>();
		if (examBO != null) {
			List<QuestionBO> questionBOs = fetchQuestionDetails(examBO);
			if (questionBOs != null && !questionBOs.isEmpty()) {
				for (QuestionBO questionBO : questionBOs) {
					Question question = prepareQuestion(questionBO);
					if (question != null) {
						questions.add(question);
					}
				}
			}
		}
		QuestionForm questionForm = new QuestionForm();
		questionForm.setQuestions(questions);
		return questionForm;
	}

	private Question prepareQuestion(QuestionBO questionBO) {
		if (questionBO == null) {
			return null;
		}
		Question question = new Question();
		question.setId(questionBO.getId());
		question.setQuestion(questionBO.getQuestion());
		question.setAnswer(questionBO.getAnswer());
		List<String> choices = new ArrayList<>();
		choices.add(questionBO.getChoiceA());
		choices.add(questionBO.getChoiceB());
		choices.add(questionBO.getChoiceC());
		choices.add(questionBO.getChoiceD());
		choices.add(questionBO.getChoiceE());
		question.setChoices(choices);
		return question;
	}

	private List<QuestionBO> fetchQuestionDetails(ExamBO examBO) {
		List<QuestionBO> questionBOs = null;
		if (examBO != null) {
			questionBOs = getQuestionService().listQuestions();
			int questionCount = examBO.getNoOfQuestion();
			Collections.shuffle(questionBOs, new Random(questionCount));
			if (questionBOs.size() > questionCount) {
				questionBOs = questionBOs.subList(0, questionCount + 1);
			}
		}
		return questionBOs;
	}

	private ExamBO fetchExamDetails() {
		List<ExamBO> examBOs = getExamService().listExams();
		// Assuming that exam table have only one record for ever.
		if (examBOs != null && examBOs.size() == 1) {
			return examBOs.get(0);
		}
		return null;
	}
}
