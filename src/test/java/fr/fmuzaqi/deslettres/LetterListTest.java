package fr.fmuzaqi.deslettres;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LetterListTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testMain() {
        LetterList.main(new String[]{});

        ArrayList<String> expectedVoyelles = new ArrayList<>(Arrays.asList("a", "e", "i", "o", "u", "y"));
        ArrayList<String> expectedConsonnes = new ArrayList<>(Arrays.asList("b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "z"));

        String expectedOutput = "Voyelles : " + expectedVoyelles + System.lineSeparator() +
                                "Consonnes : " + expectedConsonnes + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }
}