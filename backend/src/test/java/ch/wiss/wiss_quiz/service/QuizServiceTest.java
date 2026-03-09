package ch.wiss.wiss_quiz.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class QuizServiceTest {

    private QuizService service;

    @BeforeEach
    void setUp() {
        service = new QuizService(null, null);
    }
    
    // Simple example test to verify that JUnit works
    @Test
    void testSomething() {
    	assertEquals(4, 2 + 2);
    }
}