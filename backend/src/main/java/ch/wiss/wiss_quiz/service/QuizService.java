package ch.wiss.wiss_quiz.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.wiss.wiss_quiz.model.Category;
import ch.wiss.wiss_quiz.model.CategoryRepository;
import ch.wiss.wiss_quiz.model.Question;
import ch.wiss.wiss_quiz.model.QuestionRepository;

@Service
public class QuizService {

    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;

    public QuizService(QuestionRepository questionRepository,
                       CategoryRepository categoryRepository) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Question> pickQuizQuestions(List<Question> questions, int maxQuestions) {
        if (questions == null || questions.isEmpty() || maxQuestions <= 0) {
            return List.of();
        }

        List<Question> copy = new ArrayList<>(questions);

        if (copy.size() > maxQuestions) {
            Collections.shuffle(copy);
            return copy.subList(0, maxQuestions);
        }

        return copy;
    }

    public boolean hasEnoughQuestions(List<Question> questions) {
        return questions != null && questions.size() >= 3;
    }

    public double calculateScorePercent(int correct, int total) {
        if (total <= 0) {
            return 0;
        }
        return (correct * 100.0) / total;
    }

    public boolean isQuizPassed(double percent) {
        return percent >= 60;
    }

    public List<Question> getQuizQuestionsByCategoryId(Integer catId) {
        Category cat = categoryRepository.findById(catId).orElseThrow();

        List<Question> questions = questionRepository.findByCategory(cat);

        if (!hasEnoughQuestions(questions)) {
            throw new IllegalStateException("Not enough questions for quiz");
        }

        return pickQuizQuestions(questions, 3);
    }
}