package fr.brisse.deschiffres;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class NumberListTest {

    @Test
    public void testNumberListInitialization() {
        NumberList numberList = new NumberList();
        ArrayList<Integer> generatedList = numberList.getGenerateList(20);
        assertNotNull(generatedList);
        assertEquals(20, generatedList.size());
        assertEquals(24, numberList.card.size());
    }

    @Test
    public void testGetGenerateList() {
        NumberList numberList = new NumberList();
        ArrayList<Integer> generatedList = numberList.getGenerateList(5);
        assertNotNull(generatedList);
        assertEquals(5, generatedList.size());
    }

    @Test
    public void testGetGenerateListUniqueElements() {
        NumberList numberList = new NumberList();
        ArrayList<Integer> generatedList = numberList.getGenerateList(20);
        assertNotNull(generatedList);
        assertEquals(20, generatedList.size());
        for (Integer number : generatedList) {
            assertTrue(number >= 1 && number <= 100);
        }
    }

    @Test
    public void testGetGenerateListExceedingLength() {
        NumberList numberList = new NumberList();
        ArrayList<Integer> generatedList = numberList.getGenerateList(30);
        assertNotNull(generatedList);
        assertEquals(numberList.card.size(), generatedList.size()); // Since there are only 28 elements in the initial list
    }
}