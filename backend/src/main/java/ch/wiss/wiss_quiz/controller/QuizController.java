package ch.wiss.wiss_quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.wiss_quiz.model.Question;
import ch.wiss.wiss_quiz.service.QuizService;

@RestController
@RequestMapping(path = "/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping(path = "")
    public List<Question> getQuizQuestions(@RequestParam Integer cat_id) {
        return quizService.getQuizQuestionsByCategoryId(cat_id);
    }
}