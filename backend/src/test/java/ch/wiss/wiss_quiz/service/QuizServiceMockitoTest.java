package ch.wiss.wiss_quiz.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.wiss.wiss_quiz.model.CategoryRepository;
import ch.wiss.wiss_quiz.model.QuestionRepository;

@ExtendWith(MockitoExtension.class)
class QuizServiceMockitoTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private QuizService service;
    
    // Simple example test to verify that JUnit works
    @Test
    void testSomething() {
    	assertEquals(4, 2 + 2);
    }
}