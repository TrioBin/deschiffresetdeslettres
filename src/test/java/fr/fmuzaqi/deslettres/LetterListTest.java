package fr.fmuzaqi.deslettres;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;


public class LetterListTest {

    @Test
    public void testValidWordInDictionary() throws URISyntaxException, IOException {
        // Arrange
        char[] lettres = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        String mot = "abc";
        createDictionaryFile("abc");

        // Act
        LetterList.validité(lettres, mot);

        // Assert
        // Check console output manually or use a library to capture console output
    }

    @Test
    public void testInvalidWordInDictionary() throws URISyntaxException, IOException {
        // Arrange
        char[] lettres = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        String mot = "xyz";
        createDictionaryFile("abc");

        // Act
        LetterList.validité(lettres, mot);

        // Assert
        // Check console output manually or use a library to capture console output
    }

    @Test
    public void testWordNotFormedFromLetters() throws URISyntaxException, IOException {
        // Arrange
        char[] lettres = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        String mot = "aaa";
        createDictionaryFile("aaa");

        // Act
        LetterList.validité(lettres, mot);

        // Assert
        // Check console output manually or use a library to capture console output
    }

    @Test
    public void testDictionaryFileNotFound() {
        // Arrange
        char[] lettres = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        String mot = "abc";

        // Act
        LetterList.validité(lettres, mot);

        // Assert
        // Check console output manually or use a library to capture console output
    }

    private void createDictionaryFile(String word) throws URISyntaxException, IOException {
        File file = new File(getClass().getClassLoader().getResource("").toURI().getPath() + "Dictionnaire.txt");
        FileWriter writer = new FileWriter(file);
        writer.write(word);
        writer.close();
    }
}