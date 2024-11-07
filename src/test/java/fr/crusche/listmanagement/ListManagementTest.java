package fr.crusche.listmanagement;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListManagementTest {

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
    public void testShowListWithIntegers() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        ListManagement.ShowList(list);

        String expectedOutput = "1\n2\n3\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testShowListWithStrings() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");

        ListManagement.ShowList(list);

        String expectedOutput = "Hello\nWorld\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testShowListWithEmptyList() {
        ArrayList<String> list = new ArrayList<>();

        ListManagement.ShowList(list);

        String expectedOutput = "";
        assertEquals(expectedOutput, outContent.toString());
    }
}