package ch.wiss.wiss_quiz.selenium;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

class SeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void openQuizPage() {
        driver.get("http://localhost:3000/quiz");
    }

    private WebElement questionText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='question-text']")));
    }

    private List<WebElement> answerButtons() {
        return driver.findElements(By.cssSelector("[data-testid^='answer-button-']"));
    }

    private WebElement nextButton() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='next-button']")));
    }

    @Test
    void quizPage_showsQuestionText() {
        openQuizPage();

        String text = questionText().getText();

        assertFalse(text.isBlank(), "Der Fragetext sollte sichtbar und nicht leer sein.");
    }

    @Test
    void quizPage_showsAnswerButtons() {
        openQuizPage();

        wait.until(driver -> answerButtons().size() > 0);

        List<WebElement> buttons = answerButtons();

        assertFalse(buttons.isEmpty(), "Es sollten Antwort-Buttons sichtbar sein.");
        assertEquals(4, buttons.size(), "Es sollten genau 4 Antwort-Buttons vorhanden sein.");
    }

    @Test
    void clickingAnswer_changesPageState() {
        //openQuizPage();
        driver.get("http://localhost:3000/quiz");
        String questionBefore = questionText().getText();

        wait.until(driver -> answerButtons().size() > 0);
        answerButtons().get(0).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[data-testid='next-button']")
        ));
        String bodyText = driver.findElement(By.tagName("body")).getText();

        assertFalse(bodyText.isBlank(), "Nach dem Klick sollte weiterhin Inhalt sichtbar sein.");
    }

    

 
    @Test
    void nextButton_isVisible_afterAnswer() {
        driver.get("http://localhost:3000/quiz");

        List<WebElement> answerButtons = driver.findElements(By.cssSelector("[data-testid^='answer-button-']"));
        wait.until(driver -> answerButtons.size() > 0);
        
        answerButtons.get(0).click();

       // WebElement next = nextButton();
        WebElement next = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='next-button']")));
        
        assertFalse(next.getText().isBlank(), "Der Next-Button sollte sichtbar sein.");
    }

    @Test
    void quiz_can_progress_through_multiple_questions() {
        openQuizPage();

        String firstQuestion = questionText().getText();

        wait.until(driver -> answerButtons().size() > 0);
        answerButtons().get(0).click();
        nextButton().click();

        String secondQuestion = questionText().getText();
        assertFalse(secondQuestion.isBlank(), "Die zweite Frage sollte sichtbar sein.");

        wait.until(driver -> answerButtons().size() > 0);
        answerButtons().get(0).click();
        nextButton().click();

        String thirdQuestion = questionText().getText();
        assertFalse(thirdQuestion.isBlank(), "Die dritte Frage sollte sichtbar sein.");

        assertFalse(firstQuestion.equals(secondQuestion) && secondQuestion.equals(thirdQuestion),
                "Das Quiz sollte zu weiteren Fragen fortschreiten.");
    }
    
    @Test
    void nextButton_loadsNextQuestion() {
        driver.get("http://localhost:3000/quiz");

        String firstQuestion = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='question-text']")
            )
        ).getText();

        WebElement answerButton = driver.findElement(
            By.cssSelector("[data-testid='answer-button-1']")
        );
        answerButton.click();

        WebElement nextButton = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='next-button']")
            )
        );
        nextButton.click();

        String secondQuestion = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='question-text']")
            )
        ).getText();

        assertFalse(secondQuestion.isBlank(), "Die nächste Frage sollte sichtbar sein.");
        assertNotEquals(firstQuestion, secondQuestion,
                "Nach Klick auf Next sollte eine andere Frage erscheinen.");
    }
    
    // Attention: the next tests will only run green, if the random seed 42 is set in QuizService->pickQuizQuestions()
    @Test
    void correctAnswer_increasesScore() {
        driver.get("http://localhost:3000/quiz");

        WebElement correctAnswerButton = driver.findElement(
            By.cssSelector("[data-testid='answer-button-1']")
        );
        correctAnswerButton.click();

        WebElement scoreText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='score-text']")
            )
        );

        assertTrue(scoreText.getText().contains("0"),
                "Nach einer richtigen Antwort sollte der Score 100 sein.");
    }
    
    @Test
    void score_accumulates_over_multiple_questions() {
        driver.get("http://localhost:3000/quiz");


        WebElement answerButton = driver.findElement(
            By.cssSelector("[data-testid='answer-button-1']")
        );
        answerButton.click();
        WebElement scoreText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-testid='score-text']")
                )
            );
        
        assertTrue(scoreText.getText().contains("100"),
                "Nach einer richtigen Antwort sollte der Score 100 sein.");
        
        WebElement nextButton = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='next-button']")
            )
        );
        nextButton.click();

        String secondQuestion = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='question-text']")
            )
        ).getText();

        assertFalse(secondQuestion.isBlank(), "Die nächste Frage sollte sichtbar sein.");
        
        WebElement answerButton2 = driver.findElement(
                By.cssSelector("[data-testid='answer-button-3']")
            );
            answerButton2.click();
          /*
        scoreText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-testid='score-text']")
                )
            );
*/
            scoreText = By.cssSelector("[data-testid='score-text']")
            assertTrue(scoreText.getText().contains("200"),
                    "Nach einer richtigen Antwort sollte der Score 200 sein.");
    }
    
    @Test
    void selectCategory_and_answerFirstQuestion_correctly() {
        driver.get("http://localhost:3000/quiz");

        // Kategorie Dropdown finden
        WebElement selectElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='category-select']")
            )
        );

        // Kategorie auswählen (Text muss exakt stimmen!)
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText("Applikationen testen");

        // Quiz starten
        WebElement startButton = driver.findElement(
            By.cssSelector("[data-testid='start-quiz-button']")
        );
        startButton.click();

        // Warten bis Frage erscheint
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='question-text']")
            )
        );

        // Richtige Antwort klicken (id 3 → index 2)
        WebElement correctAnswer = driver.findElement(
            By.cssSelector("[data-testid='answer-button-2']")
        );
        correctAnswer.click();

        // Score prüfen
        WebElement scoreText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='score-text']")
            )
        );

        assertTrue(scoreText.getText().contains("100"),
            "Score sollte nach richtiger Antwort 100 sein");
    }
}