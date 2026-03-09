package ch.wiss.wiss_quiz.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.wiss_quiz.model.Category;
import ch.wiss.wiss_quiz.model.CategoryRepository;
import ch.wiss.wiss_quiz.model.Question;
import ch.wiss.wiss_quiz.model.QuestionRepository;
import ch.wiss.wiss_quiz.service.QuizService;

@RestController
@RequestMapping(path = "/quiz")
public class QuizController {
  private static final int MAX_QUESTIONS = 3;
  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private QuizService quizService;
  @Autowired
  private CategoryRepository categoryRepository;

  @GetMapping(path = "")
  public List<Question> getQuizQuestions(@RequestParam Integer cat_id) {

	    Category cat = categoryRepository.findById(cat_id).orElseThrow();
	    List<Question> questions = questionRepository.findByCategory(cat);

	    if (!quizService.hasEnoughQuestions(questions)) {
	        throw new IllegalStateException("Not enough questions for quiz");
	    }

	    return quizService.pickQuizQuestions(questions, MAX_QUESTIONS);
	}
}