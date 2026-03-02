package ch.wiss.wiss_quiz.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.wiss.wiss_quiz.model.Question;

@Service
public class QuizService {

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
}